package com.createfuture.training.bankaccountapi.config;

import com.createfuture.training.bankaccountapi.model.BankAccount;
import com.createfuture.training.bankaccountapi.service.BankAccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
@EnableWebMvc
public class AppConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5074")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    @Scope("singleton") // Forces singleton just like manually injected services in .NET
    public BankAccountService bankAccountService() {
        BankAccountService service = new BankAccountService();
        populateAccountData(service);
        return service;
    }

    private void populateAccountData(BankAccountService bankAccountService) {
        List<BankAccount> accounts = new ArrayList<>();
        Random rnd1 = new Random(System.currentTimeMillis());
        Random rnd2 = new Random(System.currentTimeMillis() * 2);
        Random rnd3 = new Random(System.currentTimeMillis() * 3);

        int numAccounts = 0;
        while (numAccounts < 20) {
            try {
                BigDecimal balance = BigDecimal.valueOf(rnd1.nextDouble() * (50000 - 10) + 10).setScale(2, BigDecimal.ROUND_HALF_UP);
                String[] names = {
                        "John Smith", "Maria Garcia", "Mohammed Khan", "Sophie Dubois", "Liam Johnson",
                        "Emma Martinez", "Noah Lee", "Olivia Kim", "William Chen", "Ava Wang",
                        "James Brown", "Isabella Nguyen", "Benjamin Wilson", "Mia Li", "Lucas Anderson",
                        "Charlotte Liu", "Alexander Taylor", "Amelia Patel", "Daniel Garcia", "Sophia Kim"
                };
                String name = names[rnd2.nextInt(names.length)];

                BankAccount acc = new BankAccount();
                acc.setId(numAccounts + 1);
                acc.setAccountNumber("Account " + (numAccounts + 1));
                acc.setAccountHolderName(name);
                acc.setBalance(balance.doubleValue());
                accounts.add(acc);
                accounts.add(acc);

                int transCount = 0;
                while (transCount < 100) {
                    BigDecimal transAmt = BigDecimal.valueOf(rnd2.nextDouble() * (500 - (-500)) + (-500)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    try {
                        if (transAmt.compareTo(BigDecimal.ZERO) >= 0) {
                            acc.deposit(transAmt.doubleValue(), "Credit");
                            System.out.printf("Credit: %.2f, Balance: %.2f, Account Holder: %s%n", transAmt, acc.getBalance(), acc.getAccountHolderName());
                        } else {
                            acc.withdraw(transAmt.abs().doubleValue(), "Debit");
                            System.out.printf("Debit: %.2f, Balance: %.2f, Account Holder: %s%n", transAmt.abs(), acc.getBalance(), acc.getAccountHolderName());
                        }
                    } catch (Exception ex) {
                        System.out.println("Transaction failed: " + ex.getMessage());
                    }
                    transCount++;
                }
                System.out.printf("Account: %s, Balance: %.2f, Account Holder: %s%n", acc.getAccountNumber(), acc.getBalance(), acc.getAccountHolderName());
                numAccounts++;
            } catch (Exception ex) {
                System.out.println("Account creation failed: " + ex.getMessage());
            }
        }

        for (BankAccount fromAcc : accounts) {
            for (BankAccount toAcc : accounts) {
                if (!fromAcc.equals(toAcc)) {
                    try {
                        BigDecimal transferAmt = BigDecimal.valueOf(rnd3.nextDouble() * fromAcc.getBalance()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        if (transferAmt.doubleValue() > fromAcc.getBalance()) continue;

                        fromAcc.withdraw(transferAmt.doubleValue(), "Debit");
                        toAcc.deposit(transferAmt.doubleValue(), "Credit");

                        System.out.printf("Transfer: %.2f from %s (%s) to %s (%s)%n",
                                transferAmt, fromAcc.getAccountNumber(), fromAcc.getAccountHolderName(),
                                toAcc.getAccountNumber(), toAcc.getAccountHolderName());

                    } catch (Exception ex) {
                        System.out.println("Transfer failed: " + ex.getMessage());
                    }
                }
            }
        }

        bankAccountService.initializeAccounts(accounts);
    }
}
