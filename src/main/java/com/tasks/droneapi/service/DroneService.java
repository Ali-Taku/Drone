package com.tasks.droneapi.service;

import com.tasks.droneapi.domain.Utils.ResponseHandler;
import com.tasks.droneapi.domain.Utils.State;
import com.tasks.droneapi.domain.models.Drone;
import com.tasks.droneapi.domain.models.DroneMedication;
import com.tasks.droneapi.domain.models.Medication;
import com.tasks.droneapi.domain.repositories.DroneMedicationRepository;
import com.tasks.droneapi.domain.repositories.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {

    private final DroneRepository droneRepository;
    private final DroneMedicationRepository droneMedicationRepository;

    @Autowired
    public DroneService(DroneRepository droneRepository, DroneMedicationRepository droneMedicationRepository) {
        this.droneRepository = droneRepository;
        this.droneMedicationRepository = droneMedicationRepository;
    }

    public ResponseEntity<Object> saveDrone(Drone drone){
        if(droneRepository.existsBySerialNumber(drone.getSerialNumber())){
            return ResponseHandler.generateResponse("Drone already exists", HttpStatus.CONFLICT, null);
        }
        if(drone.getBatteryCapacity()>100){
            return ResponseHandler.generateResponse("Battery capacity cannot be above 100", HttpStatus.BAD_REQUEST, null);
        }
        Drone droneObj = droneRepository.save(drone);
        return ResponseHandler.generateResponse("Success", HttpStatus.CREATED, droneObj);
    }

    public ResponseEntity<Object> updateDrone(Drone drone){
        if(!droneRepository.existsById(drone.getId())){
            return ResponseHandler.generateResponse("Drone not found", HttpStatus.NOT_FOUND, null);
        }
        if(drone.getBatteryCapacity()>100){
            return ResponseHandler.generateResponse("Battery capacity cannot be above 100", HttpStatus.BAD_REQUEST, null);
        }
        Drone droneObj = droneRepository.save(drone);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, droneObj);
    }

    public ResponseEntity<Object> getAllDrones(){
        List<Drone> drones = new ArrayList<>();
        droneRepository.findAll().forEach(drones::add);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, drones);
    }

    public ResponseEntity<Object> getAvailableDrone(){
        List<Drone> drones = droneRepository.findAllByStateAndBatteryCapacityGreaterThanEqual(State.IDLE, 25);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, drones);
    }

    public ResponseEntity<Object> checkBatteryLevel(Long id){
        Optional<Drone> drone = droneRepository.findById(id);
        return drone.map(value -> ResponseHandler.generateResponse("Success", HttpStatus.OK, value.getBatteryCapacity()))
                .orElseGet(() -> ResponseHandler.generateResponse("Drone not found", HttpStatus.NOT_FOUND, null));
    }

    public ResponseEntity<Object> getLoadedMedication(Long id) {
            Optional <Drone> drone = droneRepository.findById(id);
            List<Medication> medications = new ArrayList<>();
            if(drone.isEmpty()){
                return ResponseHandler.generateResponse("Drone does not exist", HttpStatus.NOT_FOUND, null);
            }
            List<DroneMedication> droneMedicationByDrone = droneMedicationRepository
                    .findDroneMedicationByDroneAndAndDelivered(drone.get(), false);
            droneMedicationByDrone.forEach(droneMedication -> medications.add(droneMedication.getMedication()));
            return ResponseHandler.generateResponse("Success", HttpStatus.OK, medications);

    }
}
