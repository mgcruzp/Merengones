package com.web.proyecto.repositories;

import com.web.proyecto.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // existentes
    List<Activity> findByProcess_Id(Long processId);
    boolean existsByNameIgnoreCaseAndProcess_Id(String name, Long processId);

    // NUEVOS filtros “paralelos” a ProcessRepository
    List<Activity> findByProcess_Empresa_Id(Long empresaId);
    List<Activity> findByProcess_Status(String status);
    List<Activity> findByProcess_Category(String category);
    List<Activity> findByProcess_Empresa_IdAndProcess_Status(Long empresaId, String status);
}
