package ru.devray.day13.collaboratedtesting.bulkashmak;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
                .body("symbol", notNullValue());
    }


}
