package com.web.proyecto.repositories;

import com.web.proyecto.entities.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GatewayRepository extends JpaRepository<Gateway, Long> {
    List<Gateway> findByProcessId(Long processId);
}
