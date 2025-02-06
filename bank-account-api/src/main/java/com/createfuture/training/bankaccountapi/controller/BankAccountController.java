package com.createfuture.training.bankaccountapi.controller;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import com.createfuture.training.bankaccountapi.service.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/BankAccount")
public class BankAccountController {

    private final IBankAccountService bankAccountService;

    @Autowired
    public BankAccountController(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> accounts = bankAccountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable int id) {
        BankAccount account = bankAccountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<BankAccount> createAccount(@RequestBody BankAccount account) {
        bankAccountService.createAccount(account);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(account.getId())
                        .toUri()
        ).body(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable int id, @RequestBody BankAccount account) {
        if (id != account.getId()) {
            return ResponseEntity.badRequest().build();
        }
        bankAccountService.updateAccount(account);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        bankAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
