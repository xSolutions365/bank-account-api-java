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

    /// <summary>
    /// Scenario 1: Retrieve all bank accounts
    /// Given the bank account API is running
    /// When I request all bank accounts
    /// Then I should receive a list of all accounts with a 200 OK response
    /// </summary>
    @Test
    public void getAllAccounts_ReturnsOkResponse() {
        given()
                .when()
                .get("/api/BankAccount")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    /// <summary>
    /// Scenario 2: Retrieve a bank account by ID
    /// Given the bank account API is running
    /// When I request a bank account by ID
    /// Then I should receive the account details with a 200 OK response
    /// </summary>
    @Test
    public void getAccountById_ReturnsOkResponse() {
        given()
                .when()
                .get("/api/BankAccount/1")
                .then()
                .statusCode(200)
                .body("accountNumber", equalTo("123"));
    }

    /// <summary>
    /// Scenario 3: Create a new bank account
    /// Given the bank account API is running
    /// When I create a new bank account
    /// Then I should receive a 201 Created response with the created account details
    /// </summary>
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

    /// <summary>
    /// Scenario 4: Update an existing bank account
    /// Given the bank account API is running
    /// When I update an existing bank account
    /// Then I should receive a 204 No Content response
    /// </summary>
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

    /// <summary>
    /// Scenario 5: Delete an existing bank account
    /// Given the bank account API is running
    /// When I delete an existing bank account
    /// Then I should receive a 204 No Content response
    /// </summary>
    @Test
    public void deleteAccount_ReturnsNoContentResponse() {
        given()
                .when()
                .delete("/api/BankAccount/1")
                .then()
                .statusCode(204);
    }

    /// <summary>
    /// Scenario 6: Transfer funds between two bank accounts
    /// Given two bank accounts exist in the system
    /// When I transfer a valid amount from one account to another
    /// Then the source account balance should decrease, the destination account balance should increase, and I should receive a 200 OK response
    /// </summary>
}
