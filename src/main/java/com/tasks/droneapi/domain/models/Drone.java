package com.tasks.droneapi.domain.models;

import com.tasks.droneapi.domain.Utils.Model;
import com.tasks.droneapi.domain.Utils.State;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private Model model;

    private int weightLimit;

    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state;

}
