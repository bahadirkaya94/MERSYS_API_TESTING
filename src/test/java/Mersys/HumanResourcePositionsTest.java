package Mersys;

import Mersys.Model.HumanResourcePositions;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class HumanResourcePositionsTest {

    Cookies cookies;
    HumanResourcePositions position = new HumanResourcePositions();

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
        position.setShortName(getRandomName());
        position.setTenantId("5fe0786230cc4d59295712cf");
        System.out.println("position = " + position);

        position.setId(
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(position.toString())

                        .when()
                        .post("employee-position")

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
                .post("employee-position")

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
        System.out.println("position = " + position);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position.toString())
                .when()
                .put("employee-position")

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
                .delete("employee-position/{positionID}")

                .then()
                .log().body()
                .statusCode(204)
        ;

    }

    // <------ BUG ------>
    //@Test(dependsOnMethods = "DeletePosition", priority = 5)
    //public void DeletePositioNegative() {
    //    baseURI = "https://demo.mersys.io/school-service/api/";
    //
    //    given()
    //            .cookies(cookies)
    //            .pathParam("positionID", position.getId())
    //            .when()
    //            .delete("employee-position/{positionID}")
    //
    //            .then()
    //            .log().body()
    //            .statusCode(400)
    //    ;
    //
    //}

    @Test(dependsOnMethods = "UpdatePosition", priority = 6)
    public void UpdatePositionNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        position.setName(getRandomName());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position.toString())
                .when()
                .put("employee-position")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(400)
        ;

    }



}
