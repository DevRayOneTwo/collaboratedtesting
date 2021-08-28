package ru.devray.day13.earningssurprises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.devray.day13.dto.EarningsSurprises;

import java.util.List;

import static io.restassured.RestAssured.given;

public class EarningsSurprisesTest {

    static RequestSpecification spec;

    final static String TOKEN = "c224fcc7126c0f35b156d6cb915a12a6";

    //настройка спецификации
    @BeforeEach
    void setUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://financialmodelingprep.com/")
                .setBasePath("api/v3/earnings-surprises")
                .setContentType(ContentType.JSON);
        spec = builder.build();
    }

    //проверка статус кода и наличие в header content-type
    //с FMP AAPL
    @Test
    void testStatusCodeAndContentTypeAAPL() {
        given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .log().all()
                .when()
                .get("/AAPL")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8");
    }

    //проверка статус кода и наличие в header content-type
    //с FMP FB
    @Test
    void testStatusCodeAndContentTypeFB() {
        given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .log().all()
                .when()
                .get("/FB")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8");
    }

    //негативная проверка метода get
    //не передаем в метод FMP. Ждем 404
    @Test
    void testNegativeMethodGet() {
        given().spec(spec)
                .queryParam("apikey", TOKEN)
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(404)
                .body("error", Matchers.equalTo("Not Found"));
    }

    //параметризованный тест на проверку в JSON поля date и symbol
    //подаем один RegEx и разные FMP
    @ParameterizedTest(name = "#{index} - test {1} with valid regex")
    @MethodSource("dataProvider")
    void testValidityDateAndSymbolWithDifferentFMP(String dateReg, String fmp) {
        given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .when()
                .get(fmp)
                .then()
                .log().all()
                .body("[0].date", Matchers.matchesPattern(dateReg))
                .body("[0].symbol", Matchers.equalTo(fmp));
    }

    static List<String[]> dataProvider() {
        String dateReg = "\\d{4}-\\d{2}-\\d{2}";
        String[] str1 = new String[]{dateReg, "AAPL"};
        String[] str2 = new String[]{dateReg, "FB"};
        String[] str3 = new String[]{dateReg, "GOOG"};
        String[] str4 = new String[]{dateReg, "MSFT"};
        String[] str5 = new String[]{dateReg, "NVDA"};

        return List.of(str1, str2, str3, str4, str5);
    }

    //параметризованный тест на проверку количества объектов
    @ParameterizedTest(name = "#{index} - test with {0}")
    @ValueSource(strings = {"AAPL", "FB", "GOOG", "MSFT", "NVDA"})
    void testGettingDifferentJSONObjectsAndCheckingLength(String arg) {
        //не смог придумать как по-другому получить объекты JSON, чтобы не дублировать код =(
        List<EarningsSurprises> list;
        Response response = given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .when()
                .get(arg);
        list = response.jsonPath().getList("", EarningsSurprises.class);

        given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .when()
                .get(arg)
                .then()
                .log().all()
                .body("size()", Matchers.equalTo(list.size()));
    }
}