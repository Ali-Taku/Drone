package com.tasks.droneapi.domain.repositories;

import com.tasks.droneapi.domain.models.Drone;
import com.tasks.droneapi.domain.models.DroneMedication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DroneMedicationRepository extends CrudRepository<DroneMedication, Long> {
    List<DroneMedication> findDroneMedicationByDroneAndAndDelivered(Drone drone, boolean delivered);
}
