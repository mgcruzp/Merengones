package com.merengones.wiki.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contactos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max=100, message = "El nombre no puede exceder 100 caracteres")
    @Pattern(regexp = "^[A-Z ]{1,100}$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max=100, message = "El nombre no puede exceder 100 caracteres")
    @Pattern(regexp = "^[A-Z ]{1,100}$", message = "El nombre solo puede contener letras y espacios")
    private String apellido;

    
    @NotBlank(message = "El correo es obligatorio")
    @Size(max=100, message = "El nombre no puede exceder 100 caracteres")
    @Pattern(
        regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
        message = "Correo inválido (MAYÚSCULAS, sin espacios, debe tener '@' y un punto)"
    )
    private String correo;

    @NotNull(message = "El semestre es obligatorio")
    @Min(value = 0, message = "El semestre debe ser al menos 0")
    @Max(value = 16, message = "El semestre no puede exceder 16")
    private int semestre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(length = 1000)
    private String descripcion;
}
