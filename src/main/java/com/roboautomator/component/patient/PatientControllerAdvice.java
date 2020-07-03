package com.roboautomator.component.patient;

import com.roboautomator.component.util.ValidationException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PatientControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PatientControllerValidationException.class})
    public ResponseEntity<PatientExceptionResponse> generateExceptionResponse(PatientControllerValidationException validationException) {
        return ResponseEntity.badRequest().body(PatientExceptionResponse.builder()
            .message("Validation failed")
            .errors(List.of(new ValidationException(validationException.getField(), validationException.getMessage())))
            .build());
    }
}
