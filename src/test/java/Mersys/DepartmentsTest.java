package Mersys;

import Mersys.Model.Departments;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class DepartmentsTest {

    Cookies cookies;
    Departments departments=new Departments();

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

    @Test
    public void CreateDepartments() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        departments.setName(getRandomName());
        departments.setCode(getRandomCode());
        departments.setSchoolId("5fe07e4fb064ca29931236a5");
        departments.setActive(true);

        departments.setId(
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(departments.toString())
                .when()
                .post("department")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id")
        );

    }


    @Test(dependsOnMethods = "CreateDepartments", priority = 1)
    public void CreateDepartmentsNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(departments.toString())
                .when()
                .post("department")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(400)
        ;

    }



    @Test(dependsOnMethods = "CreateDepartments", priority = 2)
    public void UpdateDepartments() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(departments.toString())
                .when()
                .put("department")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(200)
        ;

    }


    @Test(dependsOnMethods = "CreateDepartments", priority = 3)
    public void DeleteDepartments() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .when()
                .pathParam("departmentsId",departments.getId())
                .delete("department/{departmentsId}")

                .then()
                .log().body()
                .statusCode(204)
        ;

    }


    @Test(dependsOnMethods = "DeleteDepartments",priority = 4)
    public void UpdateDepartmentsNegative() {
        baseURI = "https://demo.mersys.io/school-service/api/";

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(departments.toString())
                .when()
                .put("department")

                .then()
                .contentType(ContentType.JSON)
                .log().body()
                .statusCode(400)
        ;

    }
}
