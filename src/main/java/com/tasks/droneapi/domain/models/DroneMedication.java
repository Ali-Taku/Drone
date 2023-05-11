package com.tasks.droneapi.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DroneMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Drone drone;

    @ManyToOne
    private Medication medication;

    private int quantity;

    @Column(columnDefinition = "boolean default false")
    private boolean delivered;
}
