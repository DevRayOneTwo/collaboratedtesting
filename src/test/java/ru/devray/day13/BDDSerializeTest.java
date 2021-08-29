package ru.devray.day13;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import ru.devray.day13.dto.User;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BDDSerializeTest {

    @Test
    public void testWithObjectSerialization(){

        String URI = "https://reqres.in";
        String path = "/api/users";

        List<User> users = null;

        Response response = given()
                .baseUri(URI)
                .basePath(path).header(new Header("Content-Type", "application/json"))
                .when().log().all()
                .get();

        JsonPath usersJson = response.jsonPath();
        users = usersJson.getList("data", User.class);


        users.stream().forEach(System.out::println);
    }

}
