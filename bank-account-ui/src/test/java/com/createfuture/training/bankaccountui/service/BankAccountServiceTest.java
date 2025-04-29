package com.createfuture.training.bankaccountui.service;

import com.createfuture.training.bankaccountui.model.BankAccount;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class BankAccountServiceTest {

    @Test
    void createAccount_shouldReturnCreatedAccount() {
        // Arrange
        var restTemplate = Mockito.mock(RestTemplate.class);
        var service = new BankAccountService(restTemplate);
        var newAccount = new BankAccount();
        newAccount.setId(1);
        newAccount.setAccountHolderName("Test Account");

        var createdAccount = new BankAccount();
        createdAccount.setId(1);
        createdAccount.setAccountHolderName("Test Account");

        when(restTemplate.postForObject(
                eq("http://localhost:9090/api/BankAccount"),
                eq(newAccount),
                eq(BankAccount.class)
        )).thenReturn(createdAccount);

        // Act
        var result = service.createAccount(newAccount);

        // Assert
        assertEquals(createdAccount, result);
    }

    @Test
    void getAccounts_shouldReturnListOfAccounts() {
        // Arrange
        var restTemplate = Mockito.mock(RestTemplate.class);
        var service = new BankAccountService(restTemplate);

        var account1 = new BankAccount();
        account1.setId(1);
        account1.setAccountHolderName("Account 1");

        var account2 = new BankAccount();
        account2.setId(2);
        account2.setAccountHolderName("Account 2");

        var accounts = new BankAccount[]{account1, account2};

        when(restTemplate.getForObject(
                "http://localhost:9090/api/BankAccount",
                BankAccount[].class
        )).thenReturn(accounts);

        // Act
        var result = service.getAccounts();

        // Assert
        assertEquals(List.of(account1, account2), result);
    }
}
