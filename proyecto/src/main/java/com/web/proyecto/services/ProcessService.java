package com.web.proyecto.services;
package com.web.proyecto.services;

import com.web.proyecto.entities.Process;
import com.web.proyecto.repositories.ProcessRepository;
import java.util.List;
import java.util.Optional;

//•	Crear ProcessService con: 
// create(name), get(id), list(), update(id,name), delete(id).
// Process: name no vacío. 

public class ProcessService {

    private final ProcessRepository processRepository;

    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public Process create(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre nuevo no puede estar vacio");
        }
        Process process = new Process(name);
        return processRepository.save(process);
    }

    public Optional<Process> get(Long id) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            throw new IllegalArgumentException("Proceso " + id + " no encontrado");
        }
        return process;
    }

    public List<Process> list() {
        List<Process> processes = processRepository.findAll();
        if (processes.isEmpty()) {
            throw new IllegalStateException("Proceso no encontrado");
        }
        return processes;
    }

    public Optional<Process> update(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre nuevo no puede estar vacio");
        }
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            throw new IllegalArgumentException("El proceso " + id + " no ha sido encontrado");
        }
        process.ifPresent(p -> {
            p.setName(newName);
            processRepository.save(p);
        });
        return process;
    }

    public void delete(Long id) {
        Optional<Process> process = processRepository.findById(id);
        if (process.isEmpty()) {
            throw new IllegalArgumentException("El proceso " + id + " no ha sido encontrado");
        }
        processRepository.deleteById(id);
    }
}
