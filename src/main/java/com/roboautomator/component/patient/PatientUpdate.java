package com.roboautomator.component.patient;

import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private String title;

    @NotBlank
    private String firstName;

    private String middleNames;

    @NotBlank
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
