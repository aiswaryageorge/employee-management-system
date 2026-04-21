package com.aiswarya.ems.repository;

import com.aiswarya.ems.entity.RefreshToken;
import com.aiswarya.ems.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(User user);

    void deleteByToken(String token); // Delete token by token string
    void deleteByUser(User user); // OR delete by user (better option)
}
