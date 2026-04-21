package com.aiswarya.ems.service;

import com.aiswarya.ems.entity.User;
import com.aiswarya.ems.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register new user
    public User registerUser(String username, String password, String role) {

        logger.info("Registering user: {}", username);

        // Check if user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Convert role → Spring Security format
        // Example: ADMIN → ROLE_ADMIN
        String formattedRole = "ROLE_" + role;

        // Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(password);

        // Create user with formatted role
        User user = new User(username, encodedPassword, formattedRole);

        return userRepository.save(user); // Save to DB
    }

    // Authenticate user
    public User loginUser(String username, String password) {
        logger.info("Login attempt for user: {}", username);

        User user = findByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("Invalid login attempt for user {}", username);
            throw new RuntimeException("Invalid username or password");
        }
        logger.info("User {} logged in successfully", username);
        return user;
    }

    // Find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
