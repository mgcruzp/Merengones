package com.web.proyecto.services;

import com.web.proyecto.entities.Activity;
import com.web.proyecto.entities.Edge;
import com.web.proyecto.entities.Process;
import com.web.proyecto.repositories.ActivityRepository;
import com.web.proyecto.repositories.EdgeRepository;
import com.web.proyecto.repositories.ProcessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EdgeService {

    private final EdgeRepository edgeRepository;
    private final ActivityRepository activityRepository;
    private final ProcessRepository processRepository;

    public EdgeService(EdgeRepository edgeRepository, ActivityRepository activityRepository, ProcessRepository processRepository) {
        this.edgeRepository = edgeRepository;
        this.activityRepository = activityRepository;
        this.processRepository = processRepository;
    }

    public Edge create(Long processId, Long sourceActivityId, Long targetActivityId) {
        Process process = processRepository.findById(processId)
                .orElseThrow(() -> new IllegalArgumentException("Process not found: " + processId));
        Activity source = activityRepository.findById(sourceActivityId)
                .orElseThrow(() -> new IllegalArgumentException("Source activity not found: " + sourceActivityId));
        Activity target = activityRepository.findById(targetActivityId)
                .orElseThrow(() -> new IllegalArgumentException("Target activity not found: " + targetActivityId));

        if (!source.getProcess().getId().equals(processId) || !target.getProcess().getId().equals(processId)) {
            throw new IllegalStateException("Both activities must belong to process " + processId);
        }
        if (source.getId().equals(target.getId())) {
            throw new IllegalArgumentException("source and target cannot be the same activity");
        }

        Edge e = new Edge();
        e.setProcess(process);
        e.setSource(source);
        e.setTarget(target);

        return edgeRepository.save(e);
    }

    @Transactional(readOnly = true)
    public List<Edge> listByProcess(Long processId) {
        return edgeRepository.findByProcess_Id(processId);
    }

    public void delete(Long id) {
        edgeRepository.deleteById(id);
    }
}
