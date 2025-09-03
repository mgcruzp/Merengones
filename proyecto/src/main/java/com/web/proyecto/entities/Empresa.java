package com.web.proyecto.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresa", uniqueConstraints = {
        @UniqueConstraint(name = "uk_empresa_nit", columnNames = "nit")
})
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 30)
    private String nit;

    @Column(nullable = false, length = 120)
    private String correoContacto;
}
