package com.roboautomator.component.patient.utils;

public class PatientTestUtils {

    private PatientTestUtils() {
        // STATIC HELPERS
    }

    public static String getPatientNumber() {
        var randomNumber = Math.random();
        return String.format("%.0f", Math.ceil(randomNumber * 10000000000.0));
    }
}
