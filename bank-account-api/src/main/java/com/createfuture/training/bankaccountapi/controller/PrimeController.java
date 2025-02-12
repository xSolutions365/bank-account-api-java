package com.createfuture.training.bankaccountapi.controller;

import com.createfuture.training.bankaccountapi.service.PrimeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prime")
public class PrimeController {

    private final PrimeService primeService;

    public PrimeController(PrimeService primeService) {
        this.primeService = primeService;
    }

    @GetMapping("/{number}")
    public boolean isPrime(@PathVariable int number) {
        return primeService.isPrime(number);
    }
}
