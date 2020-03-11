package com.roboautomator.component.service;

import com.roboautomator.component.model.Health;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    private final Health health;

    public HealthService(Health health) {
        this.health = health;
    }

    public Boolean isHealthOk() {
        return health.isHealthOk();
    }

    public void setHealthOk(Boolean isOk) {
        health.setHealthOk(isOk);
    }
}
