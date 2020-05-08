package com.roboautomator.component.service.health;

import com.roboautomator.component.model.HealthEntity;
import com.roboautomator.component.repository.HealthRepository;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DatabaseHealthService implements IHealthService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseHealthService.class);

    private UUID healthId;

    private final HealthRepository healthRepository;

    private final EntityManager entityManager;

    DatabaseHealthService(HealthRepository healthRepository, EntityManager entityManager) {
        this.healthRepository = healthRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Health getServiceHealth() {
        return Health.builder()
            .alive(entityManager.isOpen())
            .writable(checkRepositoryWritable())
            .readable(checkRepositoryReadable())
            .build();
    }

    private boolean checkRepositoryWritable() {
        var healthEntity = HealthEntity.builder().build();
        try {
            var savedEntity = healthRepository.save(healthEntity);

            log.info("SavedHealthEntity {}", savedEntity);

            healthId = savedEntity.getId();
        } catch (IllegalArgumentException e) {

            log.info("Could not save the entity: {}", healthEntity);
            return false;
        }
        return true;
    }

    private boolean checkRepositoryReadable() {

        log.info("Searching for Health Entry by ID: {}", healthId);

        var healthEntity = healthRepository.findById(healthId);

        log.info("RetrievedHealthEntity {}", healthEntity);

        return healthEntity.isPresent();
    }
}
