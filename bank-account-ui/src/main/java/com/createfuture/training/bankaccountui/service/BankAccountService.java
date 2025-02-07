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
            BankAccount[] accounts = restTemplate.getForObject("http://localhost:9090/api/BankAccount", BankAccount[].class);
            return accounts != null ? Arrays.asList(accounts) : List.of();
        } catch (Exception e) {
            return List.of(); // Return empty list if API call fails
        }
    }
}
