package com.roboautomator.component.service.health;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HealthService {

    private static final Logger log = LoggerFactory.getLogger(HealthService.class);

    private final DatabaseHealthService databaseHealthService;

    private Health database;

    public boolean isHealthOk() {
        database = databaseHealthService.getServiceHealth();

        log.info("Database Health: {}", database.toString());

        return (database.isAlive() && database.isReadable() && database.isWritable());
    }

    @Override
    public String toString() {
        return "HealthService(" +
            "database=" + database +
            ')';
    }
}
