package com.tasks.droneapi.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;

@Entity
@Data
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Instant createdOn;

    private int batteryLevel;

    @ManyToOne
    private Drone drone;

}
