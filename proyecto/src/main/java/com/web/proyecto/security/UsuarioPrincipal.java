package com.web.proyecto.security;

public class UsuarioPrincipal {
    private final String email;
    private final Long empresaId;

    public UsuarioPrincipal(String email, Long empresaId) {
        this.email = email;
        this.empresaId = empresaId;
    }

    public String getEmail() {
        return email;
    }

    public Long getEmpresaId() {
        return empresaId;
    }
}
