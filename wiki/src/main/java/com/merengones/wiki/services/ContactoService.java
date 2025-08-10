package com.merengones.wiki.services;

import com.merengones.wiki.models.Contacto;
import com.merengones.wiki.repositories.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }

    public List<Contacto> listar() {
        return contactoRepository.findAll();
    }

    public Optional<Contacto> buscarPorId(Long id) {
        return contactoRepository.findById(id);
    }

    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }
}
