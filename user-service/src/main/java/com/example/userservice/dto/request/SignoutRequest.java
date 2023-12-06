package com.example.userservice.dto.request;

import lombok.Data;

@Data
public class SignoutRequest {
    private String accessToken;
}
