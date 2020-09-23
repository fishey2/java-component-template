package com.roboautomator.component.patient.utils;

import java.time.Instant;

public class PatientTestUtils {

    private PatientTestUtils() {
        // STATIC HELPERS
    }

    public static String getPatientNumber() {
        var randomNumberStr = "" + Instant.now().getEpochSecond() + Instant.now().getNano();
        return randomNumberStr.substring(randomNumberStr.length() - 13, randomNumberStr.length() - 3);
    }
}
