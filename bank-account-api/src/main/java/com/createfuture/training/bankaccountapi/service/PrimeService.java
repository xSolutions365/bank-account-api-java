package com.createfuture.training.bankaccountapi.service;

import org.springframework.stereotype.Service;

@Service
public class PrimeService {
    public boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
