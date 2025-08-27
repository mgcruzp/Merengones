package com.web.proyecto.services;

import com.web.proyecto.entities.Process;
import com.web.proyecto.repositories.ProcessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProcessService {

    private final ProcessRepository processRepository;

    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    public Process create(String name) {
        Process p = new Process();
        p.setName(name);
        return processRepository.save(p);
    }

    @Transactional(readOnly = true)
    public Process get(Long id) {
        return processRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Process not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Process> list() {
        return processRepository.findAll();
    }

    public Process update(Long id, String name) {
        Process p = get(id);
        p.setName(name);
        return processRepository.save(p);
    }

    public void delete(Long id) {
        processRepository.deleteById(id);
    }
}
