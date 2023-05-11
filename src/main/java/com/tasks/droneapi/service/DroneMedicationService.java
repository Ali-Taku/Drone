package com.tasks.droneapi.service;

import com.tasks.droneapi.domain.Utils.ResponseHandler;
import com.tasks.droneapi.domain.Utils.State;
import com.tasks.droneapi.domain.dto.DroneMedicationDto;
import com.tasks.droneapi.domain.models.Drone;
import com.tasks.droneapi.domain.models.DroneMedication;
import com.tasks.droneapi.domain.models.Medication;
import com.tasks.droneapi.domain.repositories.DroneMedicationRepository;
import com.tasks.droneapi.domain.repositories.DroneRepository;
import com.tasks.droneapi.domain.repositories.MedicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DroneMedicationService {

     private final DroneRepository droneRepository;
     private final DroneMedicationRepository droneMedicationRepository;
     private final MedicationRepository medicationRepository;

     public DroneMedicationService(DroneRepository droneRepository, DroneMedicationRepository droneMedicationRepository,
                                   MedicationRepository medicationRepository) {
          this.droneRepository = droneRepository;
          this.droneMedicationRepository = droneMedicationRepository;
          this.medicationRepository = medicationRepository;
     }

     public ResponseEntity<Object> loadMedication(DroneMedicationDto droneMedicationDto){
          Optional<Drone> drone = droneRepository.findById(droneMedicationDto.drone());
          Optional<Medication> medication = medicationRepository.findById(droneMedicationDto.medication());

          if(drone.isEmpty()){
               return ResponseHandler.generateResponse("Drone not found", HttpStatus.NOT_FOUND, null);
          }
          if (medication.isEmpty()){
               return ResponseHandler.generateResponse("Medication not found", HttpStatus.NOT_FOUND, null);
          }
          float weight = medication.get().getWeight() * droneMedicationDto.quantity();
          if(drone.get().getBatteryCapacity() <= 25){
               return ResponseHandler.generateResponse("Drone battery too low", HttpStatus.NOT_FOUND, null);
          } else if (!(drone.get().getState()== State.IDLE || drone.get().getState() == State.LOADING)) {
               return ResponseHandler.generateResponse("Drone not available for loading", HttpStatus.CONFLICT, null);
          } else if (weight > drone.get().getWeightLimit()) {
               return ResponseHandler.generateResponse("Medication weight exceeds drone limit", HttpStatus.NOT_FOUND, null);
          }
          DroneMedication droneMedication = new DroneMedication();
          droneMedication.setMedication(medication.get());
          droneMedication.setDrone(drone.get());
          droneMedication.setQuantity(droneMedicationDto.quantity());
          DroneMedication droneMedicationObj = droneMedicationRepository.save(droneMedication);
          drone.get().setState(State.LOADING);
          return ResponseHandler.generateResponse("Loaded successfully", HttpStatus.CREATED, droneMedicationObj);
     }
}
