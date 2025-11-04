package com.richardz02.personal_finance.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(unique = true)
    private String username; // Username must be unique
    private String passwordHash;

    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Transaction> transactions = new ArrayList<>();

    public User() {}

    public User(String userName, String passwordHash) {
        this.username = userName;
        this.passwordHash = passwordHash;
    }

    // Getters
    public String getUserId() {
        return userId.toString();
    }

    public String getUserName() {
        return username;
    }
    
    public String getpasswordHash() {
        return passwordHash;
    }

    // Setters
    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setpasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
