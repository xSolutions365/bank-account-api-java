package com.createfuture.training.bankaccountapi.service;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountService implements IBankAccountService {
    private final List<BankAccount> accounts = new ArrayList<>();

    @Override
    public List<BankAccount> getAllAccounts() {
        return accounts;
    }

    @Override
    public BankAccount getAccountById(int id) {
        return accounts.stream()
                .filter(account -> account.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Account not found"));
    }

    @Override
    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    @Override
    public void deleteAccount(int id) {
        BankAccount account = accounts.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Account with ID " + id + " not found."));
        accounts.remove(account);
    }

    @Override
    public void createAccount(BankAccount account) {
        accounts.add(account);
    }

    @Override
    public void updateAccount(BankAccount account) {
        BankAccount existingAccount = accounts.stream()
                .filter(a -> a.getId() == account.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Account with ID " + account.getId() + " not found."));
        existingAccount.setAccountNumber(account.getAccountNumber());
        existingAccount.setAccountHolderName(account.getAccountHolderName());
        existingAccount.setBalance(account.getBalance());
    }

    @Override
    public void initializeAccounts(List<BankAccount> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
    }
}
