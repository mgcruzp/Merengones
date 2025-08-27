package com.web.proyecto.repositories;

import com.web.proyecto.entities.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EdgeRepository extends JpaRepository<Edge, Long> {
    // Para listar edges de un proceso
    List<Edge> findByProcess_Id(Long processId);
}
