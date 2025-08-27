package com.web.proyecto.repositories;

import com.web.proyecto.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    // Para listar actividades de un proceso específico
    List<Activity> findByProcess_Id(Long processId);
}
