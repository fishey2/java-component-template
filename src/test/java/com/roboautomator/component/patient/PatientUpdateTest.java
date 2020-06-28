package com.roboautomator.component.patient;

import static com.roboautomator.component.patient.utils.PatientTestUtils.getPatientNumber;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class PatientUpdateTest {

    private static final String PATIENT_TITLE = "Mr";
    private static final String PATIENT_FIRST_NAME = "Rex";
    private static final String PATIENT_MIDDLE_NAMES = "T";
    private static final String PATIENT_LAST_NAME = "Big";

    @Test
    void shouldIncludeAllFieldsInToString() {
        var patientUpdate = PatientUpdate.builder()
            .title(PATIENT_TITLE)
            .firstName(PATIENT_FIRST_NAME)
            .middleNames(PATIENT_MIDDLE_NAMES)
            .lastName(PATIENT_LAST_NAME)
            .build();

        var patientUpdateString = patientUpdate.toString();

        assertThat(patientUpdateString).contains("title=" + PATIENT_TITLE)
            .contains("firstName=" + PATIENT_FIRST_NAME)
            .contains("middleNames=" + PATIENT_MIDDLE_NAMES)
            .contains("lastName=" + PATIENT_LAST_NAME);
    }

    @Test
    void shouldIncludeAllFieldsInBuilderToString() {
        var patientUpdateBuilder = PatientUpdate.builder()
            .title(PATIENT_TITLE)
            .firstName(PATIENT_FIRST_NAME)
            .middleNames(PATIENT_MIDDLE_NAMES)
            .lastName(PATIENT_LAST_NAME);

        var patientUpdateString = patientUpdateBuilder.toString();

        assertThat(patientUpdateString).contains("title=" + PATIENT_TITLE)
            .contains("firstName=" + PATIENT_FIRST_NAME)
            .contains("middleNames=" + PATIENT_MIDDLE_NAMES)
            .contains("lastName=" + PATIENT_LAST_NAME);
    }

    @Test
    void shouldConvertToPatientEntity() {
        var patientUpdate = PatientUpdate.builder()
            .title(PATIENT_TITLE)
            .firstName(PATIENT_FIRST_NAME)
            .middleNames(PATIENT_MIDDLE_NAMES)
            .lastName(PATIENT_LAST_NAME)
            .build();

        var patientNumber = getPatientNumber();

        var patientEntity = patientUpdate.toPatientEntity(patientNumber);

        assertThat(patientEntity.getNumber()).isEqualTo(patientNumber);
        assertThat(patientEntity.getTitle()).isEqualTo(patientUpdate.getTitle());
        assertThat(patientEntity.getFirstName()).isEqualTo(patientUpdate.getFirstName());
        assertThat(patientEntity.getMiddleNames()).isEqualTo(patientUpdate.getMiddleNames());
        assertThat(patientEntity.getLastName()).isEqualTo(patientUpdate.getLastName());
    }
}
