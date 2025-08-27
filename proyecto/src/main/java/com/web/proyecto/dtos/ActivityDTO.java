package com.web.proyecto.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Long processId;
}

