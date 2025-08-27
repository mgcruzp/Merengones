package com.web.proyecto.services;

import com.web.proyecto.entities.Activity;
import com.web.proyecto.repositories.ActivityRepository;
import java.util.List;
import java.util.Optional;

//•	Crear ActivityService con: 
// create(processId,name), get(id), listByProcess(processId), rename(id,newName), delete(id).
// Activity: existe processId. 

public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity create(Long processId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de no puede estar vacio.");
        }
        Activity activity = new Activity(processId, name);
        return activityRepository.save(activity);
    }

    public Optional<Activity> get(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new IllegalArgumentException("El ID " + id + " no fue encontrado.)");
        }
        return activityRepository.findById(id);
    }

    public List<Activity> listByProcess(Long processId) {
        if (processId == null || processId <= 0) {
            throw new IllegalArgumentException("ID del proceso es invalido");
        }
        List<Activity> activities = activityRepository.findByProcessId(processId);
        if (activities.isEmpty()) {
            throw new IllegalStateException("No se encontró la actividad " + processId);
        }
        return aactivities;
    }

    public Optional<Activity> rename(Long id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nuevo nombre no puede estar vacio");
        }
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new IllegalArgumentException("La actividad" + id + " no fue encontrada");
        }
        Optional<Activity> activity = activityRepository.findById(id);
        activity.ifPresent(a -> {
            a.setName(newName);
            activityRepository.save(a);
        });
        return activity;
    }

    public void delete(Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new IllegalArgumentException("La actividad " + id + " no fue encontrada");
        }
        activityRepository.deleteById(id);
    }
}
