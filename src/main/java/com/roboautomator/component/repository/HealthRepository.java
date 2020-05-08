package com.roboautomator.component.repository;

import com.roboautomator.component.model.HealthEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRepository extends JpaRepository<HealthEntity, UUID> {
}
