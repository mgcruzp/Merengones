package com.web.proyecto.controllers;

import com.web.proyecto.dtos.ActivityDTO;
import com.web.proyecto.services.ActivityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@CrossOrigin
public class ActivityController {

    private final ActivityService service;

    @PostMapping
    public ResponseEntity<ActivityDTO> create(@Valid @RequestBody ActivityDTO dto) {
        ActivityDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/activities/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ActivityDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/process/{processId}")
    public ResponseEntity<List<ActivityDTO>> listByProcess(@PathVariable Long processId) {
        return ResponseEntity.ok(service.listByProcess(processId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> update(@PathVariable Long id, @Valid @RequestBody ActivityDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // Renombrar por conveniencia (opcional)
    @PatchMapping("/{id}/name")
    public ResponseEntity<ActivityDTO> rename(@PathVariable Long id, @RequestBody String newName) {
        return ResponseEntity.ok(service.rename(id, newName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
