package ru.devray.day13.collaboratedtesting;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class MarketCapTest {

    final static String TOKEN = "53ef908ec640538fc602f353f474534a";
    final static String URI = "https://financialmodelingprep.com/api/v3";
    final static String PATH = "/market-capitalization";

    @Test
    @DisplayName("Проверка корректности возвращаемых данных рыночной капитализации по акции")
    void testMarketCapitalization(){

        given()
                .baseUri(URI)
                .basePath(PATH)
                .log().all()
                .queryParam("apikey", TOKEN)
                        .when().get("/AAPL")
                                .then().log().all()
                        .body("[0].symbol", Matchers.equalTo("AAPL"))
                        .body("[0].date", Matchers.matchesRegex("[\\w-]+"))
                        .body("[0].marketCap", Matchers.notNullValue());


    }

}
