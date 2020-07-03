package com.roboautomator.component.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import com.roboautomator.component.util.ValidationError;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatientExceptionResponseTest {

    @Test
    void shouldBeAbleToBuildNewExceptionResponseWithMessageAndSingleErrorUsingBuilder() {
        var patientExceptionResponse = PatientExceptionResponse.builder()
            .message("Message")
            .errors(List.of(new ValidationError("field", "error")))
            .build();

        assertThat(patientExceptionResponse).isNotNull();
        assertThat(patientExceptionResponse.getMessage()).isEqualTo("Message");
        assertThat(patientExceptionResponse.getErrors())
            .extracting(ValidationError::getField, ValidationError::getError)
            .containsExactly(tuple("field", "error"));
    }

    @Test
    void shouldHaveAllValuesInToStringMethod() {
        var responseString = PatientExceptionResponse.builder()
            .message("Message")
            .errors(List.of(new ValidationError("field", "error")))
            .build().toString();

        assertThat(responseString)
            .contains("message=Message")
            .contains("field=field")
            .contains("error=error");
    }
}
