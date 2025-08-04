package com.richardz02.personal_finance.model;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

enum TransactionType {
    EXPENSE,
    INCOME
}

public class Transaction {
    private UUID id; 
    private LocalDate date;
    private TransactionType transactionType;
    private double amount; 
    private String description;

    @JsonCreator
    public Transaction(@JsonProperty("amount") double amount, 
                       @JsonProperty("description") String description, 
                       @JsonProperty("transactionType") String transactionType) {
        this.id = UUID.randomUUID();
        this.date = LocalDate.now();
        this.amount = amount;
        this.description = description;
        this.transactionType = TransactionType.valueOf(transactionType);
    }

    // Getters 
    public String getId() {
        return this.id.toString();
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getTransactionType() {
        return this.transactionType.toString();
    }

    public double getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    // Setters
    public void setTransactionType(String newTransactionType) {
        this.transactionType = TransactionType.valueOf(newTransactionType);
    }

    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
