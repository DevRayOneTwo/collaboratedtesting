package ru.devray.day13.collaboratedtesting.bulkashmak;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static io.restassured.RestAssured.given;

public class EtfList {

    final static String TOKEN = "883eb2b944916cdc008bb115eefe990b";
    final static String URI = "https://financialmodelingprep.com/api/v3";
    final static String PATH = "/etf/list";

    static RequestSpecification spec;

    @BeforeAll
    static void setUp(){
        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder
                .setBaseUri(URI)
                .setBasePath(PATH)
                .setAccept(ContentType.JSON)
                .addQueryParam("apikey", TOKEN)
                .setContentType(ContentType.ANY);

        spec = builder.build();
    }

//    get request -> response: status code == 200 OK
    @Test
    void testStatusCode() {

        given()
                .spec(spec)
                .log().uri()

                .when().get()

                .then()
                .assertThat()
                .statusCode(200);
    }

//    get request -> response: status code == 200 OK && "symbol" is not null
    @Test
    void testStatusCodeAndBodyNotNull() {

        given()
                .spec(spec)
                .log().uri()

                .when().get()

                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(notNullValue());
    }

//    Test that "name" of first array element in response is "SPY"
    @Test
    void testNameOfFirstElementIsSpy() {

        given()
                .spec(spec)
                .log().uri()

                .when().get()

                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("symbol[0]", Is.is("SPY"));
    }

//    Test that "exchange" of second array element in response contains a substr "New York Stock"
    @Test
    void testExchangeValueOfSecondElementContains() {

        given()
                .spec(spec)
                .log().uri()

                .when().get()

                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("exchange[1]", containsString("New York Stock"));
    }

//    Test that "jsonValue" of array element with an index "index" contains "name"
    @ParameterizedTest
    @MethodSource("dataProvider")
    void test(String jsonValue, String index, String name) {

        given()
                .spec(spec)
                .log().uri()

                .when().get()

                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body(String.format("%s[%s]",jsonValue, index), containsString(name));
    }

    public static List<String[]> dataProvider(){
        String[] strings1 = new String[]{"name", "0", "SPDR S&P 500 ETF Trust"};
        String[] strings2 = new String[]{"name", "1", "VanEck Vectors Gold Miners ETF"};
        String[] strings3 = new String[]{"name", "2", "iShares, Inc. - iShares MSCI Emerging Markets ETF"};
        return List.of(strings1, strings2, strings3);
    }
}
