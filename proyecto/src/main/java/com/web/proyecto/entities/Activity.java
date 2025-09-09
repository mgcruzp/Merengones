package com.web.proyecto.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre de la actividad
    @Column(nullable = false, length = 120)
    private String name;

    // Proceso al que pertenece (obligatorio)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "process_id", nullable = false)
    private Process process;

    // Aristas que salen de esta actividad
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Edge> outgoingEdges = new ArrayList<>();

    // Aristas que llegan a esta actividad
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Edge> incomingEdges = new ArrayList<>();
}
