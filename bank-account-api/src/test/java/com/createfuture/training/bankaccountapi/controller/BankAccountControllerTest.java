package com.createfuture.training.bankaccountapi.controller;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import com.createfuture.training.bankaccountapi.service.IBankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankAccountControllerTest {

    @InjectMocks
    private BankAccountController controller;

    @Mock
    private IBankAccountService mockService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllAccounts_ReturnsOkResult() {
        // Arrange
        List<BankAccount> accounts = new ArrayList<>();

        BankAccount account1 = new BankAccount();
        account1.setId(1);
        account1.setAccountNumber("123");
        account1.setAccountHolderName("John Doe");
        account1.setBalance(1000.0);
        accounts.add(account1);

        BankAccount account2 = new BankAccount();
        account2.setId(2);
        account2.setAccountNumber("456");
        account2.setAccountHolderName("Jane Doe");
        account2.setBalance(2000.0);
        accounts.add(account2);
        when(mockService.getAllAccounts()).thenReturn(accounts);

        // Act
        ResponseEntity<List<BankAccount>> result = controller.getAllAccounts();

        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(accounts, result.getBody());
    }

    @Test
    public void getAccountById_ValidId_ReturnsOkResult() {
        // Arrange
        BankAccount account = new BankAccount();
        account.setId(1);
        account.setAccountNumber("123");
        account.setAccountHolderName("John Doe");
        account.setBalance(1000.0);
        when(mockService.getAccountById(1)).thenReturn(account);

        // Act
        ResponseEntity<BankAccount> result = controller.getAccountById(1);

        // Assert
        assertEquals(200, result.getStatusCodeValue());
        assertNotNull(result.getBody());
        assertEquals(account, result.getBody());
    }
}
