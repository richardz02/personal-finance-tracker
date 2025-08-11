package com.richardz02.personal_finance.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transaction {
    @Id
    private UUID id;  // Make id the primary key in the database
    private LocalDate date;
    private TransactionType transactionType;
    private double amount; 
    private String description;

    // Required default no-args constructor since JPA uses reflection to create entity objects
    public Transaction() {
        this.id = UUID.randomUUID();
        this.date = LocalDate.now();
    }

    // Custom constructor
    public Transaction(double amount, String description, String transactionType) {
        this();
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
    public void setTransactionType(TransactionType newTransactionType) {
        this.transactionType = newTransactionType;
    }

    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }
}
