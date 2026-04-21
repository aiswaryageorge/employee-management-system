package com.aiswarya.ems.dto;

// Generic API response wrapper
public class ApiResponse<T> {

    private boolean success; // true/false
    private String message;  // message for frontend
    private T data;          // actual response

    // Constructor
    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}