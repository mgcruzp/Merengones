package com.web.proyecto.services;

import com.web.proyecto.dtos.ActivityDTO;
import com.web.proyecto.entities.Activity;
import com.web.proyecto.entities.Process;
import com.web.proyecto.repositories.ActivityRepository;
import com.web.proyecto.repositories.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ProcessRepository processRepository;

    /* ====== Mapeos ====== */
    private ActivityDTO toDTO(Activity a) {
        return ActivityDTO.builder()
                .id(a.getId())
                .name(a.getName())
                .processId(a.getProcess().getId())
                .build();
    }

    private Activity fromDTOForCreate(ActivityDTO dto, Process p) {
        return Activity.builder()
                .name(dto.getName())
                .process(p)
                .build();
    }

    /* ====== CRUD ====== */
    public ActivityDTO create(ActivityDTO dto) {
        Process p = processRepository.findById(dto.getProcessId())
                .orElseThrow(() -> new IllegalArgumentException("Proceso no encontrado: " + dto.getProcessId()));

        if (activityRepository.existsByNameIgnoreCaseAndProcess_Id(dto.getName(), dto.getProcessId())) {
            throw new IllegalArgumentException("Ya existe una actividad con ese nombre en el proceso.");
        }

        Activity saved = activityRepository.save(fromDTOForCreate(dto, p));
        return toDTO(saved);
    }

    @Transactional(readOnly = true)
    public ActivityDTO getById(Long id) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + id));
        return toDTO(a);
    }

    @Transactional(readOnly = true)
    public List<ActivityDTO> list() {
        return activityRepository.findAll().stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityDTO> listByProcess(Long processId) {
        return activityRepository.findByProcess_Id(processId).stream().map(this::toDTO).toList();
    }

    public ActivityDTO update(Long id, ActivityDTO dto) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + id));

        Long targetProcessId = (dto.getProcessId() != null) ? dto.getProcessId() : a.getProcess().getId();
        String targetName = (dto.getName() != null) ? dto.getName() : a.getName();

        if (!a.getName().equalsIgnoreCase(targetName) || !a.getProcess().getId().equals(targetProcessId)) {
            if (activityRepository.existsByNameIgnoreCaseAndProcess_Id(targetName, targetProcessId)) {
                throw new IllegalArgumentException("Ya existe una actividad con ese nombre en el proceso.");
            }
        }

        if (dto.getName() != null) a.setName(dto.getName());
        if (dto.getProcessId() != null) {
            Process p = processRepository.findById(dto.getProcessId())
                    .orElseThrow(() -> new IllegalArgumentException("Proceso no encontrado: " + dto.getProcessId()));
            a.setProcess(p);
        }

        return toDTO(activityRepository.save(a));
    }

    public ActivityDTO rename(Long id, String newName) {
        Activity a = activityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada: " + id));
        if (activityRepository.existsByNameIgnoreCaseAndProcess_Id(newName, a.getProcess().getId())) {
            throw new IllegalArgumentException("Ya existe una actividad con ese nombre en el proceso.");
        }
        a.setName(newName);
        return toDTO(activityRepository.save(a));
    }

    public void delete(Long id) {
        if (!activityRepository.existsById(id)) {
            throw new IllegalArgumentException("Actividad no encontrada: " + id);
        }
        activityRepository.deleteById(id);
    }
}
