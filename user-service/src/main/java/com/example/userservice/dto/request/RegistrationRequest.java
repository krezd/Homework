package com.example.userservice.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationRequest {
    private String name;
    private String username;
    private String password;
    private String confirmPassword;
}

