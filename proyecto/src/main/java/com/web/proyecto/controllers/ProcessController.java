package com.web.proyecto.controllers;

import com.web.proyecto.dtos.ProcessDTO;
import com.web.proyecto.entities.Process;
import com.web.proyecto.services.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/processes")
@RequiredArgsConstructor
@CrossOrigin
public class ProcessController {

    private final ProcessService service;

    @PostMapping
    public ResponseEntity<ProcessDTO> create(@RequestBody ProcessDTO dto) {
        ProcessDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/processes/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Process>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ProcessDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
