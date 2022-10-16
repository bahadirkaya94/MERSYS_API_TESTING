package Mersys;

import Mersys.Model.Attestations;
import Mersys.Model.SubjectCategories;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SubjectCategoriesTest {

    Cookies cookies;

    @BeforeClass
    public void loginMersys()
    {
        baseURI = "https://demo.mersys.io/";

        Map<String,String> credential = new HashMap<>();
        credential.put("username","richfield.edu");
        credential.put("password","Richfield2020!");
        credential.put("rememberMe","true");

        cookies =

                given()
                        .contentType(ContentType.JSON)
                        .body(credential)


                        .when()
                        .post("auth/login")


                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }

    String subjectCategoriesID;
    String subjectCategoriesName;
    String subjectCategoriesCode;
    Boolean subjectCategoriesActive;

    @Test
    public void createSubjectCategories()
    {
        subjectCategoriesName=getRandomName();
        subjectCategoriesCode=getRandomCode();
        subjectCategoriesActive=true;

        SubjectCategories subjectCategories = new SubjectCategories();
        subjectCategories.setName(subjectCategoriesName);
        subjectCategories.setCode(subjectCategoriesCode);
        subjectCategories.setActive(subjectCategoriesActive);

        subjectCategoriesID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(subjectCategories)


                        .when()
                        .post("school-service/api/subject-categories")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createSubjectCategories")
    public void createSubjectCategoriesNegative()
    {
        SubjectCategories subjectCategories = new SubjectCategories();
        subjectCategories.setName(subjectCategoriesName);
        subjectCategories.setCode(subjectCategoriesCode);
        subjectCategories.setActive(subjectCategoriesActive);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(subjectCategories)

                .when()
                .post("school-service/api/subject-categories")

                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("The Subject Category with Name \""+subjectCategoriesName+"\" already exists."))
        ;
    }

    @Test(dependsOnMethods = "createSubjectCategories")
    public void updateSubjectCategories()
    {
        subjectCategoriesName = getRandomName();
        subjectCategoriesCode = getRandomCode();
        subjectCategoriesActive = false;

        SubjectCategories subjectCategories = new SubjectCategories();
        subjectCategories.setName(subjectCategoriesName);
        subjectCategories.setCode(subjectCategoriesCode);
        subjectCategories.setActive(subjectCategoriesActive);
        subjectCategories.setId(subjectCategoriesID);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(subjectCategories)

                .when()
                .put("school-service/api/subject-categories")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(subjectCategoriesName))
        ;
    }

    @Test(dependsOnMethods = "updateSubjectCategories")
    public void deleteSubjectCategoriesById()
    {
        given()
                .cookies(cookies)
                .pathParam("subjectCategoriesID",subjectCategoriesID)

                .when()
                .delete("school-service/api/subject-categories/{subjectCategoriesID}")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteSubjectCategoriesById")
    public void deleteSubjectCategoriesByIdNegative()
    {

        given()
                .cookies(cookies)
                .pathParam("subjectCategoriesID",subjectCategoriesID)

                .when()
                .delete("school-service/api/subject-categories/{subjectCategoriesID}")


                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteSubjectCategoriesById")
    public void updateSubjectCategoriesNegative()
    {
        subjectCategoriesName = getRandomName();
        subjectCategoriesCode = getRandomCode();
        subjectCategoriesActive = true;

        SubjectCategories subjectCategories = new SubjectCategories();
        subjectCategories.setId(subjectCategoriesID);
        subjectCategories.setName(subjectCategoriesName);
        subjectCategories.setCode(subjectCategoriesCode);
        subjectCategories.setActive(subjectCategoriesActive);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(subjectCategories)

                .when()
                .put("school-service/api/subject-categories")


                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Can't find Subject Category"))

        ;
    }




    public String getRandomName()
    {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }
    public String getRandomCode()
    {
        return RandomStringUtils.randomAlphabetic(6).toLowerCase();
    }

}
