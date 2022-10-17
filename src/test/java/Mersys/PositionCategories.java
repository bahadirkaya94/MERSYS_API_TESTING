package Mersys;

import Mersys.Model.Position;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class PositionCategories {

    Cookies cookies;
    Position position=new Position();

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

    @BeforeClass
    public void loginCampus() {

        Map<String,String> loginCredentials= new HashMap<>();
        loginCredentials.put("username","richfield.edu");
        loginCredentials.put("password","Richfield2020!");
        loginCredentials.put("rememberMe", "false");
        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(loginCredentials)
                        .when()
                        .post("https://demo.mersys.io/auth/login")
                        .then()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .statusCode(200)
                        .extract().detailedCookies()
        ;
    }

    @Test(priority = 1)
    public void CreatePosition() {
        baseURI = "https://demo.mersys.io/school-service/api/";


        position.setName(getRandomName());

        position.setId(
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(position.toString())
                        .when()
                        .post("position-category")

                        .then()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        );

    }

    @Test(dependsOnMethods = "CreatePosition", priority = 2)
    public void CreatePositionNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(position.toString())
                        .when()
                        .post("position-category")

                        .then()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .statusCode(400)
        ;

    }


    @Test(dependsOnMethods = "CreatePosition", priority = 3)
    public void UpdatePosition() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        position.setName(getRandomName());

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(position.toString())
                        .when()
                        .put("position-category")

                        .then()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .statusCode(200)
        ;

    }


    @Test(dependsOnMethods = "CreatePosition", priority = 4)
    public void DeletePosition() {
        baseURI = "https://demo.mersys.io/school-service/api/";

                given()
                        .cookies(cookies)
                        .pathParam("positionID", position.getId())
                        .when()
                        .delete("position-category/{positionID}")

                        .then()
                        .log().body()
                        .statusCode(204)
        ;

    }

    @Test(dependsOnMethods = "DeletePosition", priority = 5)
    public void DeletePositioNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .pathParam("positionID", position.getId())
                .when()
                .delete("position-category/{positionID}")

                .then()
                .log().body()
                .statusCode(400)
        ;

    }

    @Test(dependsOnMethods = "UpdatePosition", priority = 6)
    public void UpdatePositionNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        position.setName(getRandomName());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position.toString())
                .when()
                .put("position-category")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(400)
        ;

    }

}
