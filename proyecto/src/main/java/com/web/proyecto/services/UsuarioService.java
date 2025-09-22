package com.web.proyecto.services;

import com.web.proyecto.dtos.UsuarioDTO;
import com.web.proyecto.entities.Empresa;
import com.web.proyecto.entities.Usuario;
import com.web.proyecto.repositories.EmpresaRepository;
import com.web.proyecto.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final EmpresaRepository empresaRepo;
    private final PasswordEncoder passwordEncoder; // inyectado

    private UsuarioDTO toDTO(Usuario u) {
        return UsuarioDTO.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .email(u.getEmail())
                // no devolvemos password aquí porque ya está WRITE_ONLY en la entidad
                .empresaId(u.getEmpresa().getId())
                .build();
    }

    private Usuario toEntity(UsuarioDTO dto) {
        Empresa empresa = empresaRepo.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada: " + dto.getEmpresaId()));

        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                // Hashear antes de guardar
                .password(passwordEncoder.encode(dto.getPassword()))
                .empresa(empresa)
                .build();
    }

    // esta es la parte de create que registra un usuario nuevo
    public UsuarioDTO create(UsuarioDTO dto) {
        if (usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con email: " + dto.getEmail());
        }
        Usuario saved = usuarioRepo.save(toEntity(dto));
        return toDTO(saved);
    }

    // esta es la parte de update que actualiza los datos de un usuario existente
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));

        if (!u.getEmail().equals(dto.getEmail()) && usuarioRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con email: " + dto.getEmail());
        }

        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());

        // Re-hashear siempre que se actualice
        u.setPassword(passwordEncoder.encode(dto.getPassword()));

        u.setEmpresa(empresaRepo.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada: " + dto.getEmpresaId())));

        return toDTO(usuarioRepo.save(u));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO getById(Long id) {
        return usuarioRepo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> list() {
        return usuarioRepo.findAll().stream().map(this::toDTO).toList();
    }

    public void delete(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado: " + id);
        }
        usuarioRepo.deleteById(id);
    }

    //filtrar usuarios por empresa
    @Transactional(readOnly = true)
public List<UsuarioDTO> listByEmpresa(Long empresaId) {
    return usuarioRepo.findAll().stream()
            .filter(u -> u.getEmpresa().getId().equals(empresaId))
            .map(this::toDTO)
            .toList();
}

}
