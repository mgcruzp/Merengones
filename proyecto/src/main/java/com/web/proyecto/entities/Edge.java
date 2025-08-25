package com.web.proyecto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "edge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45)
    private String description;

    /*Dos atributos que no alcanzo a ver */

    @Column(length = 45, nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "process_id", nullable = false)
    private Process process;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_activity_id", nullable = false)
    private Activity source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_activity_id", nullable = false)
    private Activity target;
}
