package com.createfuture.training.bankaccountapi.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrimeServiceTest {
    private final PrimeService primeService = new PrimeService();

    @Test
    void testIsPrime() {
        assertTrue(primeService.isPrime(3));
        assertFalse(primeService.isPrime(4));
    }
}
