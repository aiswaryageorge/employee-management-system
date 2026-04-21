package com.aiswarya.ems.dto;

// Response DTO
public class LogoutResponse {

    private String message;

    public LogoutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}