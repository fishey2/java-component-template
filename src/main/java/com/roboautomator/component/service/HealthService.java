package com.roboautomator.component.service;

import com.roboautomator.component.model.HealthEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HealthService {

    private final HealthEntity health;

    public boolean isHealthOk() {
        return health.isHealthOk();
    }

    public void setHealthOk(Boolean isOk) {
        health.setHealthOk(isOk);
    }
}
