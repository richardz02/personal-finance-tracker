package com.richardz02.personal_finance.model;

public class User {
    private String username;
    private String passwordHash;

    public User(String userName, String passwordHash) {
        this.username = userName;
        this.passwordHash = passwordHash;
    }

    // Getters
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
