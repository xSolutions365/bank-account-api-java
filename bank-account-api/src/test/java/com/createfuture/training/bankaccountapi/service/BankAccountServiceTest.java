package com.createfuture.training.bankaccountapi.service;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountServiceTest {

    private BankAccountService service;

    @BeforeEach
    public void setup() {
        service = new BankAccountService();
        service.initializeAccounts(new ArrayList<>()); // Clear the accounts list before each test
    }

    @Test
    public void getAllAccounts_ShouldReturnAllAccounts() {
        // Arrange
        List<BankAccount> expectedAccounts = new ArrayList<>();
        BankAccount account1 = new BankAccount();
        account1.setId(1);
        account1.setAccountNumber("123456");
        account1.setAccountHolderName("John Doe");
        account1.setBalance(1000.0);
        expectedAccounts.add(account1);

        BankAccount account2 = new BankAccount();
        account2.setId(2);
        account2.setAccountNumber("654321");
        account2.setAccountHolderName("Jane Doe");
        account2.setBalance(2000.0);
        expectedAccounts.add(account2);

        for (BankAccount account : expectedAccounts) {
            service.createAccount(account);
        }

        // Act
        List<BankAccount> accounts = service.getAllAccounts();

        // Assert
        assertEquals(expectedAccounts.size(), accounts.size());
    }

    @Test
    public void getAccountById_ValidId_ShouldReturnAccount() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1);
        account.setAccountNumber("123456");
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);
        service.createAccount(account);

        // Act
        BankAccount result = service.getAccountById(1);

        // Assert
        assertNotNull(result);
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    public void createAccount_ShouldAddAccount() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1);
        account.setAccountNumber("123456");
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);

        // Act
        service.createAccount(account);
        BankAccount result = service.getAccountById(1);

        // Assert
        assertNotNull(result);
        assertEquals(account.getAccountNumber(), result.getAccountNumber());
    }

    @Test
    public void updateAccount_ValidId_ShouldUpdateAccount() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1);
        account.setAccountNumber("123456");
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);
        service.createAccount(account);
        account.setBalance(1500.0);

        // Act
        service.updateAccount(account);
        BankAccount result = service.getAccountById(1);

        // Assert
        assertEquals(1500.0, result.getBalance());
    }

    @Test
    public void deleteAccount_ValidId_ShouldRemoveAccount() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1);
        account.setAccountNumber("123456");
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);
        service.createAccount(account);

        // Act
        service.deleteAccount(1);

        // Assert
        assertThrows(IllegalStateException.class, () -> service.getAccountById(1));
    }

    @Test
    public void initializeAccounts_ShouldClearExistingAccounts() {
        // Arrange
        BankAccount account1 = new BankAccount();
        account1.setAccountNumber("123456");
        account1.setAccountHolderName("John Doe");
        account1.setBalance(1000.0);
        service.createAccount(account1);
        List<BankAccount> accounts = new ArrayList<>();
        BankAccount account2 = new BankAccount();
        account2.setAccountNumber("654321");
        account2.setAccountHolderName("Jane Doe");
        account2.setBalance(2000.0);
        accounts.add(account2);

        // Act
        service.initializeAccounts(accounts);

        // Assert
        assertEquals(accounts.size(), service.getAllAccounts().size());
    }

    // @Test
    // public void getAccountById_InvalidId_ShouldReturnNull() {
    //     // Act
    //     BankAccount result = service.getAccountById(999); // Non-existent ID
    
    //     // Assert
    //     assertNull(result);
    // }
    
    // @Test
    // public void updateAccount_InvalidId_ShouldThrowIllegalArgumentException() {
    //     // Arrange
    //     BankAccount account = new BankAccount();
    //     account.setId(999); // Invalid ID
    //     account.setAccountNumber("123456");
    //     account.setAccountHolderName("John Doe");
    //     account.setBalance(1000.0);
    
    //     // Act & Assert
    //     assertThrows(IllegalArgumentException.class, () -> service.updateAccount(account));
    // }
    
    // @Test
    // public void deleteAccount_InvalidId_ShouldNotThrowException() {
    //     // Act & Assert
    //     assertDoesNotThrow(() -> service.deleteAccount(999)); // Non-existent ID
    // }
    
    // @Test
    // public void createAccount_DuplicateAccountNumber_ShouldThrowException() {
    //     // Arrange
    //     BankAccount account1 = new BankAccount();
    //     account1.setAccountNumber("123456");
    //     account1.setAccountHolderName("John Doe");
    //     account1.setBalance(1000.0);
    
    //     BankAccount account2 = new BankAccount();
    //     account2.setAccountNumber("123456"); // Duplicate account number
    //     account2.setAccountHolderName("Jane Doe");
    //     account2.setBalance(2000.0);
    
    //     // Act
    //     service.createAccount(account1);
    
    //     // Assert
    //     assertThrows(IllegalArgumentException.class, () -> service.createAccount(account2));
    // }
}
