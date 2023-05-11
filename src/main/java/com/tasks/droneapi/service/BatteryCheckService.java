package com.tasks.droneapi.service;

import com.tasks.droneapi.domain.models.AuditTrail;
import com.tasks.droneapi.domain.models.Drone;
import com.tasks.droneapi.domain.repositories.AuditTrailRepository;
import com.tasks.droneapi.domain.repositories.DroneRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryCheckService {

    private final DroneRepository droneRepository;
    private final AuditTrailRepository auditTrailRepository;

    public BatteryCheckService(DroneRepository droneRepository, AuditTrailRepository auditTrailRepository) {
        this.droneRepository = droneRepository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Scheduled(fixedRate = 60000) //run every minute
    public void checkBatteryLevel(){
        List<Drone> drones = (List<Drone>) droneRepository.findAll();
        for(Drone drone : drones){
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setBatteryLevel(drone.getBatteryCapacity());
            auditTrail.setDrone(drone);
            auditTrailRepository.save(auditTrail);
        }
    }
}
