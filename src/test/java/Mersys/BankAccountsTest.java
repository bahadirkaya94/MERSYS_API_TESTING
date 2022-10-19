package Mersys;

import Mersys.Model.BankAccounts;
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

public class BankAccountsTest {

    Cookies cookies;

    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(8).toLowerCase();
    }

    public String getRandomCode(int number) {
        return RandomStringUtils.randomAlphabetic(number).toLowerCase();
    }

    public String getRandomIntCode() {
        return RandomStringUtils.randomNumeric(20);
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

    String bnkID;
    String bnkName;
    String bnkIban;
    String bnkIntegrationCode;

    @Test
    public void createBankAccounts() {
        bnkName = getRandomName();
        bnkIntegrationCode = getRandomCode(3);
        bnkIban = getRandomCode(2) + "" + getRandomIntCode();
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setSchoolId("6343bf893ed01f0dc03a509a");
        bankAccounts.setIntegrationCode(bnkIntegrationCode);
        bankAccounts.setName(bnkName);
        bankAccounts.setIban(bnkIban);
        bankAccounts.setCurrency("USD");


        bnkID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(bankAccounts)

                        .when()
                        .post("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }


    @Test(dependsOnMethods = "createBankAccounts")
    public void createBankAccountsNegative() {

        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setId(bnkID);
        bankAccounts.setName(bnkName);
        bankAccounts.setIban(bnkIban);
        bankAccounts.setIntegrationCode(bnkIntegrationCode);
        bankAccounts.setCurrency("USD");
        bankAccounts.setSchoolId("6343bf893ed01f0dc03a509a");
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(bankAccounts)
                .when()
                .post("school-service/api/bank-accounts")
                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("To create a new Bank Account, the field 'ID' must be empty."))
        ;
    }

    @Test(dependsOnMethods = "createBankAccounts")
    public void updateBankAccounts() {

        bnkName = getRandomName();
        bnkIntegrationCode = getRandomCode(3);
        bnkIban = getRandomCode(2) + "" + getRandomIntCode();
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setId(bnkID);
        bankAccounts.setName(bnkName);
        bankAccounts.setIban(bnkName);
        bankAccounts.setIntegrationCode(bnkIntegrationCode);
        bankAccounts.setCurrency("USD");
        bankAccounts.setSchoolId("6343bf893ed01f0dc03a509a");
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(bankAccounts)
                .when()
                .put("school-service/api/bank-accounts")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(bnkName))
        ;
    }

    @Test(dependsOnMethods = "updateBankAccounts")
    public void deleteBankAccounts() {

        given()
                .cookies(cookies)
                .pathParam("bnkID", bnkID)
                .when()
                .delete("school-service/api/bank-accounts/{bnkID}")
                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "deleteBankAccounts")
    public void deleteBankAccountsNegative() {

        given()
                .cookies(cookies)
                .pathParam("bnkID", bnkID)
                .when()
                .delete("school-service/api/bank-accounts/{bnkID}")
                .then()
                .log().body()
                .statusCode(400)
        ;
    }

    @Test(dependsOnMethods = "deleteBankAccounts")
    public void updatePositionsNegative() {

        bnkName = getRandomName();
        bnkIntegrationCode = getRandomCode(3);
        bnkIban = getRandomCode(2) + "" + getRandomIntCode();
        BankAccounts bankAccounts = new BankAccounts();
        bankAccounts.setId(bnkID);
        bankAccounts.setName(bnkName);
        bankAccounts.setIban(bnkName);
        bankAccounts.setIntegrationCode(bnkIntegrationCode);
        bankAccounts.setCurrency("USD");
        bankAccounts.setSchoolId("6343bf893ed01f0dc03a509a");
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(bankAccounts)
                .when()
                .put("school-service/api/bank-accounts")
                .then()
                .log().body()
                .statusCode(400)
                .body("message", equalTo("Can't find Bank Account"))
        ;
    }
}






