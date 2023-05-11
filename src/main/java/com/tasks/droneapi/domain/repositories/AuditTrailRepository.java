package com.tasks.droneapi.domain.repositories;

import com.tasks.droneapi.domain.models.AuditTrail;
import org.springframework.data.repository.CrudRepository;

public interface AuditTrailRepository extends CrudRepository<AuditTrail, Long> {
}
