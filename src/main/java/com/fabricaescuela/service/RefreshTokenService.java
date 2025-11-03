package com.fabricaescuela.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fabricaescuela.models.entity.RefreshToken;
import com.fabricaescuela.models.entity.Usuario;
import com.fabricaescuela.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(Usuario user) {
        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedAt(Instant.now());
        // set expiry to now + jwtExpiration (for example purposes)
        token.setExpiryDate(Instant.now().plusMillis(jwtExpiration));
        token.setRevoked(false);
        token.setUsuario(user);
        return refreshTokenRepository.save(token);
    }
}
