package com.web.proyecto.repositories;

import com.web.proyecto.entities.Process;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Long> {
    List<Process> findByCompanyId(Long companyId);

    List<Process> findByStatus(String status);

    List<Process> findByCategory(String category);

    List<Process> findByCompanyIdAndStatus(Long companyId, String status);
}
