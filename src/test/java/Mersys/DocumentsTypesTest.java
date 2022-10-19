package Mersys;


import Mersys.Model.DocumentsTypes;
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

public class DocumentsTypesTest {


    Cookies cookies;

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    @BeforeClass
    public void loginMersys() {
        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

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

    String dtID;
    String dtName;

    @Test
    public void createDocumentTypes() {
        dtName = getRandomName();
        DocumentsTypes documentsTypes = new DocumentsTypes();
        documentsTypes.setAttachmentStages(new String[]{"CERTIFICATE"});
        documentsTypes.setSchoolId("6343bf893ed01f0dc03a509a");
        documentsTypes.setName(dtName);

        dtID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(documentsTypes)

                        .when()
                        .post("school-service/api/attachments")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }


    @Test(dependsOnMethods = "createDocumentTypes")
    public void createDocumentTypesNegative() {

        DocumentsTypes documentsTypes = new DocumentsTypes();
        documentsTypes.setId(dtID);
        documentsTypes.setSchoolId("6343bf893ed01f0dc03a509a");
        documentsTypes.setAttachmentStages(new String[]{"CONTRACT"});
        documentsTypes.setName(dtName);
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentsTypes)
                .when()
                .post("school-service/api/attachments")
                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Please provide valid data to create 'Attachment Type', your 'Attachment Type' already created"))
        ;
    }


    @Test(dependsOnMethods = "createDocumentTypes")
    public void updateDocumentTypes() {


        DocumentsTypes documentsTypes = new DocumentsTypes();
        documentsTypes.setId(dtID);
        documentsTypes.setName(dtName);
        documentsTypes.setAttachmentStages(new String[]{"STUDENT_REGISTRATION"});
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentsTypes)
                .when()
                .body("school-service/api/attachments")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(dtName))
        ;
    }
    @Test(dependsOnMethods = "updateDocumentTypes")
    public void deleteDocumentTypes() {

        given()
                .cookies(cookies)
                .pathParam("dtID", dtID)
                .when()
                .delete("school-service/api/attachments/{dtID}")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }
    @Test(dependsOnMethods = "deleteDocumentTypes")
    public void deleteDocumentTypesNegative() {

        given()
                .cookies(cookies)
                .pathParam("dtID", dtID)
                .when()
                .delete("school-service/api/attachments/{dtID}")
                .then()
                .log().body()
                .statusCode(400)
        ;
    }
    @Test(dependsOnMethods = "deleteDocumentTypes")
    public void updateDocumentTypesNegative() {


        DocumentsTypes documentsTypes = new DocumentsTypes();
        documentsTypes.setId(dtID);
        documentsTypes.setName(dtName);
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(documentsTypes)
                .when()
                .put("school-service/api/attestation")
                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Can't find Attestation"))
        ;
    }
}







