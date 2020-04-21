package com.roboautomator.component.service;

import com.roboautomator.component.model.Health;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthServiceTest {

    private HealthService healthService;

    @BeforeEach
    void createHealthService() {
        healthService = new HealthService(new Health());
    }

    @Test
    public void checkHealthServiceIsHealthOk() {
        assertThat(healthService.isHealthOk()).isTrue();
    }

    @Test
    public void checkSetHealthIsOkUsingServiceOverridesDefaultValue() {
        healthService.setHealthOk(false);
        assertThat(healthService.isHealthOk()).isFalse();
    }
}
