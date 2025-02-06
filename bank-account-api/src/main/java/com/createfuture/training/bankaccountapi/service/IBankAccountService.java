package com.createfuture.training.bankaccountapi.service;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import java.util.List;

public interface IBankAccountService {
    void initializeAccounts(List<BankAccount> accounts);
    List<BankAccount> getAllAccounts();
    BankAccount getAccountById(int id);
    void addAccount(BankAccount account);
    void deleteAccount(int id);
    void createAccount(BankAccount account);
    void updateAccount(BankAccount account);
}
