package com.merengones.wiki.repositories;

import com.merengones.wiki.models.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {

    // Ejemplos de métodos de consulta derivados (opcionales)
    List<Contacto> findByCorreo(String correo);
    List<Contacto> findByNombreContainingIgnoreCase(String nombre);
}
