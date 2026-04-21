package com.aiswarya.ems.repository;

import com.aiswarya.ems.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username (used for login)
    Optional<User> findByUsername(String username);
}