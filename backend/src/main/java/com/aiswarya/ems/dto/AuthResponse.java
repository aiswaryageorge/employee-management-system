package com.aiswarya.ems.dto;

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String username;
    private String role;

    // Constructor
    public AuthResponse(String token, String refreshToken, String username, String role) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
    public String getUsername() {
        return username;
    }
    public String getRole() { return role; }
}