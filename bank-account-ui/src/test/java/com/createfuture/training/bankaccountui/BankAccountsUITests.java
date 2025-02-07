package com.createfuture.training.bankaccountui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountsUITests {

    private WebDriver driver;

    @BeforeAll
    void setupWebDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Wait for elements to be ready
        driver.get("http://localhost:8080/bankaccounts"); // Adjust if necessary
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Bank Accounts Page Should Display Table")
    void bankAccountsPageShouldDisplayTable() {
        // Find the table element
        WebElement table = driver.findElement(By.tagName("table"));
        Assertions.assertNotNull(table, "Bank accounts table should be visible.");
    }

    @Test
    @DisplayName("Table Should Have Rows of Accounts")
    void tableShouldHaveRowsOfAccounts() {
        List<WebElement> rows = driver.findElements(By.cssSelector("tbody tr"));
        Assertions.assertFalse(rows.isEmpty(), "Bank accounts table should have at least one row.");
    }

    @Test
    @DisplayName("Table Columns Should Be Correct")
    void tableColumnsShouldBeCorrect() {
        WebElement header = driver.findElement(By.cssSelector("thead tr"));
        List<WebElement> columns = header.findElements(By.tagName("th"));

        Assertions.assertEquals(3, columns.size(), "Table should have 3 columns: Account Number, Account Holder, and Balance");

        Assertions.assertEquals("Account Number", columns.get(0).getText().trim());
        Assertions.assertEquals("Account Holder", columns.get(1).getText().trim());
        Assertions.assertEquals("Balance", columns.get(2).getText().trim());
    }
}
