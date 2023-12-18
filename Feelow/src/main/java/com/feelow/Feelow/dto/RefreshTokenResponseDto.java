package com.feelow.Feelow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResponseDto {
    private final String refreshToken;
    private final int refreshTokenExprTime;
}
