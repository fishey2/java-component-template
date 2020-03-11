package com.roboautomator.component.model;

import org.springframework.stereotype.Component;

@Component
public class Health {

    private boolean healthOk = true;

    public boolean isHealthOk() {
        return healthOk;
    }

    public void setHealthOk(boolean healthOk) {
        this.healthOk = healthOk;
    }
}