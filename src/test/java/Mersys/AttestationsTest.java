package Mersys;

import Mersys.Model.Attestations;
import io.restassured.http.Cookies;
import org.testng.annotations.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AttestationsTest {

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

    String attestationsID;
    String attestationsName;

    @Test
    public void createAttestations()
    {
        attestationsName=getRandomName();

        Attestations attestations = new Attestations();
        attestations.setName(attestationsName);

        attestationsID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(attestations)


                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
                ;
    }

    @Test(dependsOnMethods = "createAttestations")
    public void createAttestationsNegative()
    {
        Attestations attestations = new Attestations();
        attestations.setName(attestationsName);

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(attestations)

                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(400)
                        .body("message", equalTo("The Attestation  with Name \""+attestationsName+"\" already exists."))
                ;
    }

    @Test(dependsOnMethods = "createAttestations")
    public void updateAttestations()
    {
        attestationsName = getRandomName();

        Attestations attestations = new Attestations();
        attestations.setName(attestationsName);
        attestations.setId(attestationsID);

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(attestations)

                        .when()
                        .put("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("name", equalTo(attestationsName))
                ;
    }

    @Test(dependsOnMethods = "updateAttestations")
    public void deleteAttestationsById()
    {
                given()
                        .cookies(cookies)
                        .pathParam("attestationsID",attestationsID)

                        .when()
                        .delete("school-service/api/attestation/{attestationsID}")

                        .then()
                        .log().body()
                        .statusCode(204)
                ;
    }

    @Test(dependsOnMethods = "deleteAttestationsById")
    public void deleteAttestationsByIdNegative()
    {

        given()
                .cookies(cookies)
                .pathParam("attestationsID",attestationsID)

                .when()
                .delete("school-service/api/attestation/{attestationsID}")


                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteAttestationsById")
    public void updateAttestationsNegative()
    {
        attestationsName = getRandomName();

        Attestations attestations = new Attestations();
        attestations.setId(attestationsID);
        attestations.setName(attestationsName);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(attestations)

                .when()
                .put("school-service/api/attestation")


                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Can't find Attestation"))

        ;
    }




    public String getRandomName()
    {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

}
