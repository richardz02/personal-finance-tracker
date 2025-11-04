package com.richardz02.personal_finance.dto.transaction;

import java.time.LocalDate;

import com.richardz02.personal_finance.model.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TransactionResponseDTO {

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private double amount;

    @Size(max = 200)
    private String description;

    public TransactionResponseDTO(LocalDate date, TransactionType transactionType, double amount, String description) {
        this.date = date;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }
}
