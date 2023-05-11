package com.tasks.droneapi.controller;

import com.tasks.droneapi.domain.dto.MedicationDto;
import com.tasks.droneapi.service.MedicationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/medications")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllMedications(){
        return medicationService.getAllMedications();
    }

    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addMedication(@ModelAttribute MedicationDto medicationDto){

        return medicationService.addMedication(medicationDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedication(@PathVariable Long id){
        return medicationService.getMedication(id);
    }
}
