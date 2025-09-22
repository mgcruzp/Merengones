package com.web.proyecto.controllers;

import com.web.proyecto.dtos.UsuarioDTO;
import com.web.proyecto.entities.Usuario;
import com.web.proyecto.repositories.UsuarioRepository;
import com.web.proyecto.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class LoginController {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO dto) {
        Usuario user = usuarioRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas"));
        }

        // ahora también pasamos empresaId
        String token = tokenProvider.generateToken(user.getEmail(), user.getEmpresa().getId());

        return ResponseEntity.ok(Map.of("token", token));
    }
}
