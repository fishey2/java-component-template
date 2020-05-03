package com.roboautomator.component.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthEntityTest {

    private HealthEntity health;

    @BeforeEach
    public void beforeEach() {
        health = new HealthEntity();
    }

    @Test
    void testHealthDefaultValueIsOk() {
        assertThat(health.isHealthOk()).isTrue();
    }

    @Test
    void testSetHealthOverridesDefaultValue() {
        health.setHealthOk(false);
        assertThat(health.isHealthOk()).isFalse();
    }
}
