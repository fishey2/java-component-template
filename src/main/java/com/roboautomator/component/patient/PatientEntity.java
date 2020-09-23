package com.roboautomator.component.patient;

import com.roboautomator.component.DefaultEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "patient")
@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class PatientEntity extends DefaultEntity {
    private String number;
    private String title;
    private String firstName;
    private String middleNames;
    private String lastName;

    public PatientEntity update(PatientUpdate patientUpdate) {
        title = patientUpdate.getTitle();
        firstName = patientUpdate.getFirstName();
        middleNames = patientUpdate.getMiddleNames();
        lastName = patientUpdate.getLastName();
        return this;
    }
}
