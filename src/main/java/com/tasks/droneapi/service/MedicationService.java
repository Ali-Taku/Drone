package com.tasks.droneapi.service;

import com.tasks.droneapi.domain.Utils.ResponseHandler;
import com.tasks.droneapi.domain.dto.MedicationDto;
import com.tasks.droneapi.domain.models.Medication;
import com.tasks.droneapi.domain.repositories.MedicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public ResponseEntity<Object> getAllMedications(){
        List<Medication> medications = new ArrayList<>();
        medicationRepository.findAll().forEach(medications::add);
        return ResponseHandler.generateResponse("Success", HttpStatus.OK, medications);
    }

    public ResponseEntity<Object> getMedication(Long id){
        Optional<Medication> medication = medicationRepository.findById(id);
        return medication.map(value -> ResponseHandler.generateResponse("Successful", HttpStatus.OK, value))
                .orElseGet(() -> ResponseHandler.generateResponse("No medication found",
                HttpStatus.NOT_FOUND, null));
    }

    public ResponseEntity<Object> addMedication(MedicationDto medicationDto){
        if(medicationRepository.existsByCode(medicationDto.code())){
            String message = "Medication with that code exists already";
            return ResponseHandler.generateResponse(message, HttpStatus.CONFLICT, null);
        }
        // home path
        String home = System.getProperty("user.dir");
        // storage path
        String path = home + File.separator + "media";
        MultipartFile picture = medicationDto.picture();
        // destination creation
        File storage = new File(path);
        if (!storage.exists()) {
            storage.mkdirs();
        }
        // file path
        String filePath = path + File.separator + picture.getOriginalFilename();
        // create new file
        File file = new File(filePath);
        // save
        try {
            picture.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Medication medication = new Medication();
        medication.setCode(medicationDto.code());
        medication.setName(medicationDto.name());
        medication.setWeight(medicationDto.weight());
        medication.setPicture(filePath);
        Medication medicationObject = medicationRepository.save(medication);
        return ResponseHandler.generateResponse("Medication added successfully",
                HttpStatus.CREATED, medicationObject);
    }
}
