package Mersys;

import Mersys.Model.GradeLevels;
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
public class GradeLevelsTest {

    Cookies cookies;

    GradeLevels Grade = new GradeLevels();


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

    String GradeID;
    String GradeName99;
    String GradeShortName99;

    @Test
    public void createCountry()
    {
        GradeName99=getRandomName2();
        GradeShortName99=getRandomName2();


        Grade.setName99(GradeName99);
        Grade.setShorname99(GradeShortName99);
        Grade.setActive(true);


        System.out.println("Yaz1:"+Grade);
        Grade.setId(
                given()

                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(Grade.toString())

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        )   ;
        GradeID=Grade.getId();
    }

    public String getRandomName2() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode(int number) {
        return RandomStringUtils.randomAlphabetic(number).toLowerCase();
    }


    public String getRandomCode2() {
        return RandomStringUtils.randomAlphabetic(3).toLowerCase();
    }

    @Test(dependsOnMethods = "createCountry")
    public void createCountryNegative()
    {
        System.out.println("Yaz2:"+Grade);
        Grade.setId(null);
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(Grade.toString())

                .when()
                .post("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("The Grade Level with Name \""+Grade.getName99()+"\" already exists."))
        ;
    }

    @Test(dependsOnMethods = "createCountry")
    public void updateCountry()
    {

        GradeLevels Grade = new GradeLevels();
        Grade.setName99(getRandomName2());
        Grade.setShorname99(getRandomName2());
        Grade.setId(GradeID);

        System.out.println("GradeUPtade: = " + Grade);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(Grade.toString())

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(Grade.getName99()))
        ;
    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountryById()
    {

        given()
                .cookies(cookies)
                .pathParam("GradeID", GradeID)

                .when()
                .delete("school-service/api/grade-levels/{GradeID}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteCountryById")
    public void deleteCountryByIdNegative()
    {
        given()
                .cookies(cookies)
                .pathParam("GradeID", GradeID)
                .log().uri()
                .when()
                .delete("school-service/api/grade-levels/{GradeID}")

                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteCountryById")
    public void updateCountryNegative()
    {
        Grade.setName99(getRandomName2());
        Grade.setShorname99(getRandomName2());

        System.out.println("GradeNeg = " + Grade);
        Grade.setId(GradeID);
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(Grade.toString())

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Grade Level not found."))
        ;
    }

}
