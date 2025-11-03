package com.fabricaescuela.controller;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;
}
