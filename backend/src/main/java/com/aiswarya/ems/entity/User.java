package com.aiswarya.ems.entity;

import jakarta.persistence.*;

@Entity // This class maps to a database table
@Table(name = "users") // Table name in MySQL
public class User {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment ID
    private Long id;

    @Column(unique = true, nullable = false) // Username must be unique and not null
    private String username;

    @Column(nullable = false) // Password cannot be null
    private String password;

    private String role; // ADMIN or USER

    // Default constructor (required by JPA)
    public User() {
    }

    // Constructor
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}