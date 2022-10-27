package Mersys;

import Mersys.Model.Fields;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.math3.random.RandomGenerator;
import org.testng.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;
public class FieldsTest {
    Cookies cookies;

    Fields Field = new Fields();

    @BeforeClass
    public void loginCampus() {
        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        //.log().cookies()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String FieldsID;
    String FieldsName;
    String FieldsCode;

    @Test
    public void createCountry()
    {
        FieldsName=getRandomName();
        FieldsCode=getRandomCode();


        Field.setNameFull(FieldsName); // generateCountrName
        Field.setCode(FieldsCode); // generateCountrCode

        Field.setId88(
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(Field.toString())

                        .when()
                        .post("school-service/api/entity-field")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        )  ;

        FieldsID=Field.getId88();

    }
    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative()
    {
     //   System.out.println("Yaz2:"+Field);
        Field.setId88(null);
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(Field.toString())

                .when()
                .post("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("The SchoolMessages.EntityField.Title with Name \""+Field.getNameFull()+"\" already exists."))
        ;
    }
    @Test(dependsOnMethods = "createCountry")
    public void updateCountry()
    {

        Fields Field = new Fields();
        Field.setNameFull(getRandomName());
        Field.setCode(getRandomCode());
        Field.setId88(FieldsID);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(Field.toString())

                .when()
                .put("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(Field.getNameFull()))
        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountryById()
    {

        given()
                .cookies(cookies)
                .pathParam("FieldsID",FieldsID)

                .when()
                .delete("school-service/api/entity-field/{FieldsID}")

                .then()
                .log().body()
                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deleteCountryById")
    public void deleteCountryByIdNegative()
    {

        given()
                .cookies(cookies)
                .pathParam("FieldsID", FieldsID)
                .log().uri()
                .when()
                .delete("school-service/api/entity-field/{FieldsID}")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteCountryById")
    public void updateCountryNegative()
    {
        Field.setNameFull(getRandomName());
        Field.setCode(getRandomCode());

        System.out.println("GradeNeg = " + Field);
        Field.setId88(FieldsID);
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(Field.toString())

                .when()
                .put("school-service/api/entity-field")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("EntityField not found"))
        ;
    }



}
