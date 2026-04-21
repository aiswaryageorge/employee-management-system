package com.aiswarya.ems.dto;

// DTO = Data Transfer Object (used for API request/response)
public class RefreshTokenRequest {

    private String refreshToken; // token sent from client

    // Getter
    public String getRefreshToken() {
        return refreshToken;
    }

    // Setter
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}