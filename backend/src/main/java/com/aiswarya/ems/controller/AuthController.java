package com.aiswarya.ems.controller;

import com.aiswarya.ems.dto.*;
import com.aiswarya.ems.entity.RefreshToken;
import com.aiswarya.ems.entity.User;
import com.aiswarya.ems.security.CustomUserDetailsService;
import com.aiswarya.ems.service.RefreshTokenService;
import com.aiswarya.ems.service.UserService;
import com.aiswarya.ems.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(UserService userService,
                          JwtService jwtService,
                          RefreshTokenService refreshTokenService,
                          CustomUserDetailsService userDetailsService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService = userDetailsService;
    }

    // REGISTER
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody RegisterRequest request) {

        userService.registerUser(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
        );

        return "User registered successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

        User user = userService.loginUser(
                request.getUsername(),
                request.getPassword()
        );

        // Convert User → UserDetails (Spring Security format)
        System.out.println("Role from DB: " + user.getRole());
        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole()))
                );


        // Generate JWT
        String accessToken = jwtService.generateToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new ApiResponse<>(
                true,
                "Login successful",
                new AuthResponse(
                        accessToken,
                        refreshToken.getToken(),
                        user.getUsername(),
                        user.getRole()
                )
        );
    }
    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {

        // Step 1: Rotate token (IMPORTANT CHANGE)
        RefreshToken newRefreshToken = refreshTokenService.rotateToken(request.getRefreshToken());

        // Step 2: Get user
        User user = newRefreshToken.getUser();

        // Step 3: Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        // Step 4: Generate new access token
        String newAccessToken = jwtService.generateToken(userDetails);

        // Step 5: Return BOTH tokens
        return new RefreshTokenResponse(
                newAccessToken,
                newRefreshToken.getToken()
        );
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@RequestBody LogoutRequest request) {

        // Step 1: Delete refresh token
        refreshTokenService.deleteByToken(request.getRefreshToken());

        // Step 2: Return response
        return new LogoutResponse("Logged out successfully");
    }
}