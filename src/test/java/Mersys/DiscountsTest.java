package Mersys;

import Mersys.Model.Discounts;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DiscountsTest {

    Cookies cookies;

    Discounts discounts = new Discounts();

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
    public void CreateDiscount() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        discounts.setDescription(getRandomName());
        discounts.setCode(getRandomCode());

        discounts.setId(
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(discounts.toString())
                        .when()
                        .post("discounts")

                        .then()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        );

    }

    @Test(dependsOnMethods = "CreateDiscount", priority = 2)
    public void CreateDiscountNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(discounts.toString())
                .when()
                .post("discounts")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(400)
        ;

    }

    @Test(dependsOnMethods = "CreateDiscount", priority = 3)
    public void UpdateDiscount() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        discounts.setDescription(getRandomName());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(discounts.toString())
                .when()
                .put("discounts")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(200)
        ;

    }

    @Test(dependsOnMethods = "CreateDiscount", priority = 4)
    public void DeleteDiscount() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .pathParam("discountID", discounts.getId())
                .when()
                .delete("discounts/{discountID}")

                .then()
                .log().body()
                .statusCode(200)
        ;

    }

    @Test(dependsOnMethods = "CreateDiscount", priority = 5)
    public void DeleteDiscountNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .pathParam("discountID", discounts.getId())
                .when()
                .delete("discounts/{discountID}")

                .then()
                .log().body()
                .statusCode(400)
        ;

    }

    @Test(dependsOnMethods = "CreateDiscount", priority = 6)
    public void UpdateDiscountNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        discounts.setDescription(getRandomName());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(discounts.toString())
                .when()
                .put("discounts")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(400)
        ;

    }

}
