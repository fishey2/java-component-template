package com.roboautomator.component.controller;

import com.roboautomator.component.service.HealthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public ResponseEntity<String> getHealth() {

        return ResponseEntity
                .status((healthService.isHealthOk())
                        ? HttpStatus.OK
                        : HttpStatus.SERVICE_UNAVAILABLE)
                .body("{}");
    }
}