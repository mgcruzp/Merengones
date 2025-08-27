package com.web.proyecto.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EdgeDTO {

    @NotNull
    private Long id;

    @NotNull
    private Long processId;

    @NotNull
    private Long sourceId;

    @NotNull
    private Long targetId;
}

