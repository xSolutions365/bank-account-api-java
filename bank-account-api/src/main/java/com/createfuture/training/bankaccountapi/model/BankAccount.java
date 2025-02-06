package com.createfuture.training.bankaccountapi.model;

public class BankAccount {
    private int id;
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    // Default constructor
    public BankAccount() {
        this.balance = 0.0;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    // Methods
    public void deposit(double amount, String transactionType) {
        if (!transactionType.toLowerCase().endsWith("credit")) {
            throw new IllegalArgumentException("Transaction type must be Credit.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    public void withdraw(double amount, String transactionType) {
        if (!transactionType.toLowerCase().endsWith("debit")) {
            throw new IllegalArgumentException("Transaction type must be Debit.");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > this.balance) {
            throw new IllegalStateException("Insufficient funds.");
        }
        this.balance -= amount;
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
        if (amount > this.balance) {
            throw new IllegalStateException("Insufficient funds.");
        }
        this.balance -= amount;
        toAccount.balance += amount;
    }
}
