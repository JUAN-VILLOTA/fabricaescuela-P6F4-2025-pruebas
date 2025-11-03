package com.fabricaescuela.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", nullable = false, length = 255)
    private String token;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "revoked")
    private Boolean revoked = false;

    @Column(name = "ip_address", length = 255)
    private String ipAddress;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}
