package com.richardz02.personal_finance.model;

import java.time.LocalDate;
import java.util.UUID;

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

    public Transaction(double amount, String description, String transactionType) {
        this.id = UUID.randomUUID();
        this.date = LocalDate.now();
        this.transactionType = TransactionType.valueOf(transactionType);
        this.amount = amount;
        this.description = description;
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
