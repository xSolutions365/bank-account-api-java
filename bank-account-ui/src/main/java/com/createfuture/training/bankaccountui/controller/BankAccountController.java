package com.createfuture.training.bankaccountui.controller;

import com.createfuture.training.bankaccountui.model.BankAccount;
import com.createfuture.training.bankaccountui.service.BankAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/bankaccounts")
    public String getBankAccounts(Model model) {
        List<BankAccount> accounts = bankAccountService.getAccounts();
        model.addAttribute("accounts", accounts);
        return "bankaccounts"; // Renders bankaccounts.html
    }
}
