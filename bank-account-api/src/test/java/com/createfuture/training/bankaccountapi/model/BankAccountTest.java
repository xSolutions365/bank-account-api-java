package com.createfuture.training.bankaccountapi.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void deposit_ShouldIncreaseBalance_WhenTransactionTypeIsAtmCredit() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = 100.0;

        // Act
        account.deposit(depositAmount, "ATM Credit");

        // Assert
        assertThat(account.getBalance()).isEqualTo(depositAmount);
    }

    @Test
    public void deposit_ShouldIncreaseBalance_WhenTransactionTypeIsChequeCredit() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = 100.0;

        // Act
        account.deposit(depositAmount, "Cheque Credit");

        // Assert
        assertThat(account.getBalance()).isEqualTo(depositAmount);
    }

    @Test
    public void deposit_ShouldThrowIllegalArgumentException_WhenTransactionTypeIsNotCredit() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = 100.0;

        // Act & Assert
        assertThatThrownBy(() -> account.deposit(depositAmount, "ATM Debit"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Transaction type must be Credit.");
    }

    @Test
    public void deposit_ShouldThrowIllegalArgumentException_WhenAmountIsNotPositive() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = -100.0;

        // Act & Assert
        assertThatThrownBy(() -> account.deposit(depositAmount, "ATM Credit"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Deposit amount must be positive.");
    }

    @Test
    public void withdraw_ShouldDecreaseBalance_WhenTransactionTypeIsAtmDebit() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = 100.0;
        double withdrawAmount = 50.0;
        account.deposit(depositAmount, "ATM Credit");

        // Act
        account.withdraw(withdrawAmount, "ATM Debit");

        // Assert
        assertThat(account.getBalance()).isEqualTo(depositAmount - withdrawAmount);
    }

    @Test
    public void withdraw_ShouldDecreaseBalance_WhenTransactionTypeIsDirectDebit() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = 100.0;
        double withdrawAmount = 50.0;
        account.deposit(depositAmount, "ATM Credit");

        // Act
        account.withdraw(withdrawAmount, "Direct Debit");

        // Assert
        assertThat(account.getBalance()).isEqualTo(depositAmount - withdrawAmount);
    }

    @Test
    public void withdraw_ShouldThrowIllegalArgumentException_WhenTransactionTypeIsNotDebit() {
        // Arrange
        BankAccount account = new BankAccount();
        double withdrawAmount = 50.0;

        // Act & Assert
        assertThatThrownBy(() -> account.withdraw(withdrawAmount, "ATM Credit"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Transaction type must be Debit.");
    }

    @Test
    public void withdraw_ShouldThrowIllegalArgumentException_WhenAmountIsNotPositive() {
        // Arrange
        BankAccount account = new BankAccount();
        double withdrawAmount = -50.0;

        // Act & Assert
        assertThatThrownBy(() -> account.withdraw(withdrawAmount, "ATM Debit"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Withdrawal amount must be positive.");
    }

    @Test
    public void withdraw_ShouldThrowIllegalStateException_WhenInsufficientFunds() {
        // Arrange
        BankAccount account = new BankAccount();
        double withdrawAmount = 50.0;

        // Act & Assert
        assertThatThrownBy(() -> account.withdraw(withdrawAmount, "ATM Debit"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Insufficient funds.");
    }

    @Test
    public void deposit_ShouldIncreaseBalanceMultipleTimes() {
        // Arrange
        BankAccount account = new BankAccount();
        double firstDeposit = 100.0;
        double secondDeposit = 200.0;

        // Act
        account.deposit(firstDeposit, "Cheque Credit");
        account.deposit(secondDeposit, "Cheque Credit");

        // Assert
        assertThat(account.getBalance()).isEqualTo(firstDeposit + secondDeposit);
    }

    @Test
    public void withdraw_ShouldThrowIllegalStateException_WhenWithdrawMoreThanBalance() {
        // Arrange
        BankAccount account = new BankAccount();
        double depositAmount = 100.0;
        double withdrawAmount = 150.0;
        account.deposit(depositAmount, "ATM Credit");

        // Act & Assert
        assertThatThrownBy(() -> account.withdraw(withdrawAmount, "ATM Debit"))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Insufficient funds.");
    }
}
