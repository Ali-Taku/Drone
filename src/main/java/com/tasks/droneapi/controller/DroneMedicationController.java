package com.tasks.droneapi.controller;

import com.tasks.droneapi.domain.dto.DroneMedicationDto;
import com.tasks.droneapi.domain.models.DroneMedication;
import com.tasks.droneapi.service.DroneMedicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/drone-medication")
public class DroneMedicationController {
    private final DroneMedicationService droneMedicationService;

    public DroneMedicationController(DroneMedicationService droneMedicationService) {
        this.droneMedicationService = droneMedicationService;
    }

    @PostMapping("/load")
    public ResponseEntity<Object> load(@RequestBody DroneMedicationDto droneMedicationDto){
        return droneMedicationService.loadMedication(droneMedicationDto);
    }
}
