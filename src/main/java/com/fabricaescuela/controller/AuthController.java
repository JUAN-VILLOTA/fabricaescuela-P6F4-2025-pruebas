package com.fabricaescuela.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabricaescuela.models.entity.RefreshToken;
import com.fabricaescuela.models.entity.Usuario;
import com.fabricaescuela.models.entity.Permiso;
import com.fabricaescuela.service.UsuarioService;
import com.fabricaescuela.service.RefreshTokenService;
import com.fabricaescuela.security.JwtUtil;
import com.fabricaescuela.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (usuarioService.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Usuario u = usuarioService.register(req.getUsername(), req.getPassword(), req.getCorreo(), req.getRole());
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        var userOpt = usuarioService.findByUsername(req.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        Usuario user = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String roleName = user.getRol() != null ? user.getRol().getNombre() : "USER";
        List<String> permisos = List.of();
        if (user.getRol() != null && user.getRol().getPermisos() != null) {
            permisos = user.getRol().getPermisos().stream().map(Permiso::getNombre).collect(Collectors.toList());
        }

        String accessToken = jwtUtil.generateToken(user.getUsername(), roleName, permisos);

        RefreshToken refresh = refreshTokenService.createRefreshToken(user);

        AuthResponse resp = new AuthResponse();
        resp.setAccessToken(accessToken);
        resp.setRefreshToken(refresh.getToken());
        resp.setExpiresAt(Instant.now().plusMillis( (long)1000 * 60 * 60 *24));

        return ResponseEntity.ok(resp);
    }
}
