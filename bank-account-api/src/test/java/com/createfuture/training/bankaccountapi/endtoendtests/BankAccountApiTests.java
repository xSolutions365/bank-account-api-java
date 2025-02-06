package com.createfuture.training.bankaccountapi.endtoendtests;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import com.createfuture.training.bankaccountapi.service.IBankAccountService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BankAccountApiTests {

    @LocalServerPort
    private int port;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private IBankAccountService bankAccountService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        bankAccountService.initializeAccounts(Arrays.asList(
            new BankAccount() {{
                setId(1);
                setAccountNumber("123");
                setAccountHolderName("John Doe");
                setBalance(1000.0);
            }},
            new BankAccount() {{
                setId(2);
                setAccountNumber("456");
                setAccountHolderName("Jane Doe");
                setBalance(2000.0);
            }}
        ));
    }

    @AfterEach
    public void tearDown() {
        bankAccountService.initializeAccounts(Arrays.asList());
    }

    @Test
    public void getAllAccounts_ReturnsOkResponse() {
        given()
            .when()
            .get("/api/BankAccount")
            .then()
            .statusCode(200)
            .body("$", not(empty()));
    }

    @Test
    public void getAccountById_ReturnsOkResponse() {
        given()
            .when()
            .get("/api/BankAccount/1")
            .then()
            .statusCode(200)
            .body("accountNumber", equalTo("123"));
    }

    @Test
    public void createAccount_ReturnsCreatedResponse() {
        BankAccount newAccount = new BankAccount() {{
            setId(3);
            setAccountNumber("789");
            setAccountHolderName("Alice Doe");
            setBalance(3000.0);
        }};

        given()
            .contentType(ContentType.JSON)
            .body(newAccount)
            .when()
            .post("/api/BankAccount")
            .then()
            .statusCode(201)
            .body("accountNumber", equalTo("789"));
    }

    @Test
    public void updateAccount_ReturnsNoContentResponse() {
        BankAccount updatedAccount = new BankAccount() {{
            setId(1);
            setAccountNumber("123");
            setAccountHolderName("John Doe Updated");
            setBalance(1500.0);
        }};

        given()
            .contentType(ContentType.JSON)
            .body(updatedAccount)
            .when()
            .put("/api/BankAccount/1")
            .then()
            .statusCode(204);
    }

    @Test
    public void deleteAccount_ReturnsNoContentResponse() {
        given()
            .when()
            .delete("/api/BankAccount/1")
            .then()
            .statusCode(204);
    }
}
