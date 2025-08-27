package com.web.proyecto.services;

import com.web.proyecto.entities.Activity;
import com.web.proyecto.entities.Process;
import com.web.proyecto.repositories.ActivityRepository;
import com.web.proyecto.repositories.ProcessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ProcessRepository processRepository;

    public ActivityService(ActivityRepository activityRepository, ProcessRepository processRepository) {
        this.activityRepository = activityRepository;
        this.processRepository = processRepository;
    }

    public Activity create(Long processId, String name) {
    Process process = processRepository.findById(processId)
            .orElseThrow(() -> new IllegalArgumentException("Process not found: " + processId));
    Activity a = new Activity();
    a.setName(name);
    a.setProcess(process);
    return activityRepository.save(a);
}

    @Transactional(readOnly = true)
    public Activity get(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Activity> listByProcess(Long processId) {
        return activityRepository.findByProcess_Id(processId);
    }

    public Activity rename(Long id, String newName) {
        Activity a = get(id);
        a.setName(newName);
        return activityRepository.save(a);
    }

    public void delete(Long id) {
        activityRepository.deleteById(id);
    }
}
