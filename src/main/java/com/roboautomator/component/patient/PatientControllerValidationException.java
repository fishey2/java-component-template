package com.roboautomator.component.patient;

import lombok.Getter;

@Getter
public class PatientControllerValidationException extends RuntimeException {

    private final String field;

    public PatientControllerValidationException(String exceptionMessage, String field) {
        super(exceptionMessage);
        this.field = field;
    }
}
