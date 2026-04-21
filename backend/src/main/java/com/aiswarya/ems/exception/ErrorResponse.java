package com.aiswarya.ems.exception;

import java.time.LocalDateTime;

// Standard API error response
public class ErrorResponse {

    private LocalDateTime timestamp; // when error happened
    private int status;              // HTTP status code
    private String error;            // error type
    private String message;          // actual message
    private String path;             // API path

    // Constructor
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
    public String getPath() {
        return path;
    }
}