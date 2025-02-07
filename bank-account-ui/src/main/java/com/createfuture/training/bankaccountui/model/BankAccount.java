package com.createfuture.training.bankaccountui.model;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class BankAccount {

    private int id;

    @NotBlank
    private String accountNumber;

    @NotBlank
    private String accountHolderName;

    private BigDecimal balance;

    // Default constructor
    public BankAccount() {
        this.accountNumber = "";
        this.accountHolderName = "";
        this.balance = BigDecimal.ZERO;
    }

    // Parameterized constructor
    public BankAccount(int id, String accountNumber, String accountHolderName, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
