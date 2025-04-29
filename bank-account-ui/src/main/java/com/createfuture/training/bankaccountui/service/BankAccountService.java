package com.createfuture.training.bankaccountui.service;

import com.createfuture.training.bankaccountui.model.BankAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {

    private final RestTemplate restTemplate;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BankAccount> getAccounts() {
        try {
            BankAccount[] accounts = restTemplate.getForObject("http://bank-account-api:9090/api/BankAccount", BankAccount[].class);
            return accounts != null ? Arrays.asList(accounts) : List.of();
        } catch (Exception e) {
            return List.of(); // Return empty list if API call fails
        }
    }

    public BankAccount createAccount(BankAccount newAccount) {
        try {
            var createdAccount = restTemplate.postForObject("http://bank-account-api:9090/api/BankAccount", newAccount, BankAccount.class);
            return createdAccount;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create account: " + e.getMessage(), e);
        }
    }
}
