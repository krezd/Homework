package com.example.userservice.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthResponseGenerator {
    @Value("${jwt.tokenTtl}")
    private Long ttl;
    public AuthResponse generateAuthResponse(String token, String refreshToken) {
        return new AuthResponse(token,refreshToken,ttl);
    }
}
