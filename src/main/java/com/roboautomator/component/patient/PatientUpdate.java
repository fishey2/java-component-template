package com.roboautomator.component.patient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatientUpdate {
    private String title;
    private String firstName;
    private String middleNames;
    private String lastName;

    public PatientEntity toPatientEntity(String patientNumber) {
        return PatientEntity.builder()
            .number(patientNumber)
            .title(getTitle())
            .firstName(getFirstName())
            .middleNames(getMiddleNames())
            .lastName(getLastName())
            .build();
    }
}
