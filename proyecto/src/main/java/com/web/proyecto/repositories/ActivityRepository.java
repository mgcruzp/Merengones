package com.web.proyecto.repositories;

import com.web.proyecto.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    // Por proceso
    List<Activity> findByProcess_Id(Long processId);
    boolean existsByNameIgnoreCaseAndProcess_Id(String name, Long processId);

    // Paralelos a ProcessRepository (OJO: Process tiene empresaId, NO 'empresa')
    List<Activity> findByProcess_EmpresaId(Long empresaId);
    List<Activity> findByProcess_Status(String status);
    List<Activity> findByProcess_Category(String category);
    List<Activity> findByProcess_EmpresaIdAndProcess_Status(Long empresaId, String status);
}
