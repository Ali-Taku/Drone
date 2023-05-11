package com.tasks.droneapi.domain.repositories;

import com.tasks.droneapi.domain.models.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication, Long> {
    boolean existsByCode(String code);
}
