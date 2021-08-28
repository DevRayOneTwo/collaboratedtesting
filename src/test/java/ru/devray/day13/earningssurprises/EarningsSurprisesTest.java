package ru.devray.day13.earningssurprises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;

public class EarningsSurprisesTest {

    static RequestSpecification spec;

    final static String TOKEN = "c224fcc7126c0f35b156d6cb915a12a6";
//    final static String URI = "https://financialmodelingprep.com/";
//    final static String PATH = "api/v3/earnings-surprises";

    @BeforeEach
    void setUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://financialmodelingprep.com/")
                .setBasePath("api/v3/earnings-surprises")
                .setContentType(ContentType.JSON);
        spec = builder.build();
    }

    @Test
    void testStatusCodeAndContentType() {
        given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .log().all()
                .when()
                .get("/AAPL")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json;charset=UTF-8");
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void testValidityDateAndSymbol(String dateReg, String symbolReg) {
        given()
                .spec(spec)
                .queryParam("apikey", TOKEN)
                .when()
                .get("/AAPL")
                .then()
                .log().all()
                .body("[0].date", Matchers.matchesPattern(dateReg))
                .body("[0].symbol", Matchers.matchesPattern(symbolReg));
    }


    static List<String[]> dataProvider() {
        String[] str1 = new String[]{"^\\d{4}-\\d{2}-\\d{2}$", "[A-Z]+"};
        List<String[]> list = new ArrayList<>();
        list.add(str1);

        return list;
    }
}

//получение объектов json
//        List<String> list;
//        Response response = given()
//                .spec(spec)
//                .queryParam("apikey", TOKEN)
//                .when()
//                .get("/AAPL");
//        list = response.jsonPath().getList("");
