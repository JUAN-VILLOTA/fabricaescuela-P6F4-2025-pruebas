package com.fabricaescuela.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fabricaescuela.models.entity.Role;
import com.fabricaescuela.models.entity.Usuario;
import com.fabricaescuela.repository.RoleRepository;
import com.fabricaescuela.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario register(String username, String rawPassword, String correo, String roleName) {
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setCorreo(correo);
        u.setActivo(true);
        u.setFechaCreacion(Instant.now());

        if (roleName != null) {
            Role r = roleRepository.findByNombre(roleName);
            u.setRol(r);
        }

        return usuarioRepository.save(u);
    }
}
