package com.roboautomator.component.patient;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PatientEntityTest {

    private static final String PATIENT_NUMBER = "1234567890";
    private static final String PATIENT_TITLE = "Dr";
    private static final String PATIENT_FIRST_NAME = "Phil";
    private static final String PATIENT_MIDDLE_NAMES = "V. J.";
    private static final String PATIENT_LAST_NAME = "Someone";

    @Test
    void shouldBeAbleToBuildNewEntityUsingBuilder() {
        var testId = UUID.randomUUID();
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var patientEntity = PatientEntity
            .builder()
            .id(testId)
            .number(PATIENT_NUMBER)
            .title(PATIENT_TITLE)
            .firstName(PATIENT_FIRST_NAME)
            .middleNames(PATIENT_MIDDLE_NAMES)
            .lastName(PATIENT_LAST_NAME)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .build();

        assertThat(patientEntity.getId()).isEqualTo(testId);
        assertThat(patientEntity.getNumber()).isEqualTo(PATIENT_NUMBER);
        assertThat(patientEntity.getTitle()).isEqualTo(PATIENT_TITLE);
        assertThat(patientEntity.getFirstName()).isEqualTo(PATIENT_FIRST_NAME);
        assertThat(patientEntity.getMiddleNames()).isEqualTo(PATIENT_MIDDLE_NAMES);
        assertThat(patientEntity.getLastName()).isEqualTo(PATIENT_LAST_NAME);
        assertThat(patientEntity.getCreatedAt()).isEqualTo(createdAt);
        assertThat(patientEntity.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldHaveAllValuesInToStringMethod() {
        var testId = UUID.randomUUID();
        var patientEntity = PatientEntity
            .builder()
            .id(testId)
            .number(PATIENT_NUMBER)
            .title(PATIENT_TITLE)
            .firstName(PATIENT_FIRST_NAME)
            .middleNames(PATIENT_MIDDLE_NAMES)
            .lastName(PATIENT_LAST_NAME)
            .build();

        assertThat(patientEntity.toString()).contains("id=" + testId);
        assertThat(patientEntity.toString()).contains("number=" + PATIENT_NUMBER);
        assertThat(patientEntity.toString()).contains("title=" + PATIENT_TITLE);
        assertThat(patientEntity.toString()).contains("firstName=" + PATIENT_FIRST_NAME);
        assertThat(patientEntity.toString()).contains("middleNames=" + PATIENT_MIDDLE_NAMES);
        assertThat(patientEntity.toString()).contains("lastName=" + PATIENT_LAST_NAME);
    }

    @Test
    void shouldBuildMessageEntity() {
        var testId = UUID.randomUUID();
        var patientEntityBuilder = PatientEntity
            .builder()
            .id(testId)
            .number(PATIENT_NUMBER)
            .title(PATIENT_TITLE)
            .firstName(PATIENT_FIRST_NAME)
            .middleNames(PATIENT_MIDDLE_NAMES)
            .lastName(PATIENT_LAST_NAME);

        assertThat(patientEntityBuilder.toString()).contains("id=" + testId);
        assertThat(patientEntityBuilder.toString()).contains("number=" + PATIENT_NUMBER);
        assertThat(patientEntityBuilder.toString()).contains("title=" + PATIENT_TITLE);
        assertThat(patientEntityBuilder.toString()).contains("firstName=" + PATIENT_FIRST_NAME);
        assertThat(patientEntityBuilder.toString()).contains("middleNames=" + PATIENT_MIDDLE_NAMES);
        assertThat(patientEntityBuilder.toString()).contains("lastName=" + PATIENT_LAST_NAME);
        assertThat(patientEntityBuilder.build()).isNotNull();
    }
}
