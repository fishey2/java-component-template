package com.roboautomator.component.patient.utils;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class PatientTestUtilTest {

    @Test
    void shouldReturnTenCharacterNumberFromGetPatientNumber() {
        var patientNumber = PatientTestUtils.getPatientNumber();

        assertThat(patientNumber)
            .hasSize(10)
            .matches("([0-9]+)");
    }
}
