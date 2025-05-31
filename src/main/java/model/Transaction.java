package model;

import model.Category;
import model.Type;
import java.time.LocalDate;


public class Transaction {
    // Static variable to assign next transaction id
    private static int nextId = 1;

    private int id;
    private double amount;
    private Type type;
    private Category category;
    private String description;
    private LocalDate date;

    public Transaction(double amount, Type type, Category category, String description) {
        this.id = nextId++;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.date = LocalDate.now();
    }

    public int getId() {
        return this.id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }
}
