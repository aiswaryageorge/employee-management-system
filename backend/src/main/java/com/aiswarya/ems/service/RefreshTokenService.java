package com.aiswarya.ems.service;

import com.aiswarya.ems.entity.RefreshToken;
import com.aiswarya.ems.entity.User;
import com.aiswarya.ems.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository repository;// DB access layer
    // Constructor injection (Spring automatically injects repository)
    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }
    //create or update refresh token for a user
    public RefreshToken createRefreshToken(User user) {
        // Check if user already has a refreshtoken in DB
        Optional<RefreshToken> existingToken =
                repository.findByUser(user);
         //if token exist-->update it
        if (existingToken.isPresent()) {
            RefreshToken token = existingToken.get(); // get() → extract value from Optional

        token.setToken(UUID.randomUUID().toString()); //Generate new random token string
        token.setExpiryDate(LocalDateTime.now().plusDays(7));// Set expiry to 7 days from now

        return repository.save(token);// Save updated token in DB
    }
        // Create new token if token does not exists
        RefreshToken token = new RefreshToken();
        token.setUser(user); // Link token to user
        token.setToken(UUID.randomUUID().toString()); // Generate token
        token.setExpiryDate(LocalDateTime.now().plusDays(7)); // Expiry

        return repository.save(token); // Save new token
        }
    //Verify → check exists + not expired
    public RefreshToken verifyToken(String token) {
        //find token in DB using token string
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token")); //if not found->throw error
        //check if token expired
        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired"); //if expired throw error
        }

        return refreshToken;//return valid token
    }
    // 🔹 Delete refresh token (logout)
    public void deleteByToken(String token) {

        // Find token in DB
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        // Delete token → logout user
        repository.delete(refreshToken);
    }
    // 🔹 Rotate refresh token (delete old + create new)
    public RefreshToken rotateToken(String oldToken) {

        // Step 1: Find existing token
        RefreshToken existing = repository.findByToken(oldToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Step 2: Check expiry
        if (existing.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        // Step 3: Get user
        User user = existing.getUser();

        // Step 4: Delete old token
        repository.delete(existing);

        // Step 5: Create new token
        RefreshToken newToken = new RefreshToken();
        newToken.setUser(user);
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setExpiryDate(LocalDateTime.now().plusDays(7));

        return repository.save(newToken);
    }
}
