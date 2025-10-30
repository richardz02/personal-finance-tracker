package com.richardz02.personal_finance.dto.user;

public class UserAuthResponseDTO {
    private String userId;
    private String username;

    public UserAuthResponseDTO(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
