package com.tasks.droneapi.controller;

import com.tasks.droneapi.domain.models.Drone;
import com.tasks.droneapi.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/drones")
public class DroneController {
    private final DroneService droneService;

    public DroneController(DroneService droneService) {

        this.droneService = droneService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> saveDrone(@RequestBody Drone drone){

        return droneService.saveDrone(drone);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateDrone(@RequestBody Drone drone){

        return droneService.updateDrone(drone);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllDrones(){

        return droneService.getAllDrones();
    }

    @GetMapping("/ready-to-load")
    public ResponseEntity<Object> getAllReadyToLoadDrones(){

        return droneService.getAvailableDrone();
    }

    @GetMapping("/{id}/check-battery-level")
    public ResponseEntity<Object> checkBatteryLevel(@PathVariable("id") Long id){

        return droneService.checkBatteryLevel(id);
    }

    @GetMapping("/{id}/loaded-medication")
    public ResponseEntity<Object> getLoadedMedication(@PathVariable Long id){
        return droneService.getLoadedMedication(id);
    }
}
