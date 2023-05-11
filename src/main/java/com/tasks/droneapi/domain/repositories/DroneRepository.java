package com.tasks.droneapi.domain.repositories;

import com.tasks.droneapi.domain.Utils.State;
import com.tasks.droneapi.domain.models.Drone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DroneRepository extends CrudRepository<Drone, Long> {
    boolean existsBySerialNumber(String serialNumber);
    List<Drone> findAllByStateAndBatteryCapacityGreaterThanEqual(State state, int batteryCapacity);
}
