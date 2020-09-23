package com.roboautomator.component.patient;

import com.roboautomator.component.util.ValidationError;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PatientControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PatientControllerValidationException.class})
    public ResponseEntity<PatientExceptionResponse> handlePatientControllerValidationException(
        PatientControllerValidationException validationException) {

        var errors = List.of(new ValidationError(validationException.getField(), validationException.getMessage()));

        return ResponseEntity.badRequest().body(getValidationFailedResponse(errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<PatientExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        var errors = new ArrayList<ValidationError>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ValidationError(fieldName, errorMessage));
        });

        return ResponseEntity.badRequest().body(getValidationFailedResponse(errors));
    }

    private PatientExceptionResponse getValidationFailedResponse(List<ValidationError> errors) {
        return PatientExceptionResponse.builder()
            .message("Validation failed")
            .errors(errors)
            .build();
    }
}
