package com.homework.home.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

public class TokensRequest {
    private String accessToken;
    private String refreshToken;
}
