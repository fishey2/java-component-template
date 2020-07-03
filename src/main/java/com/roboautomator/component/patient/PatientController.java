package com.roboautomator.component.patient;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;

    @PostMapping(value = "/{patientNumber}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void createOrUpdatePatient(@PathVariable String patientNumber, @RequestBody PatientUpdate patientUpdate) {

        if (!isValidPatientNumber(patientNumber)) {
            throw new PatientControllerValidationException("The path parameter \"patientNumber\" should be 10 numbers exactly", "patientNumber");
        }

        log.info("Received request to create or update patient with patient number \"{}\"", patientNumber);
        patientRepository.save(getOrCreateEntity(patientNumber, patientUpdate));
    }

    private boolean isValidPatientNumber(String patientNumber) {
        return patientNumber.matches("^[0-9]{10}$");
    }

    private PatientEntity getOrCreateEntity(String patientNumber, PatientUpdate patientUpdate) {
        var patient = patientRepository.findByNumber(patientNumber);

        PatientEntity patientEntity;

        if(patient.isPresent()) {
            log.info("Found existing record for patient \"{}\", updating.", patientNumber);
            patientEntity = patient.get().update(patientUpdate);
        } else {
            log.info("Patient does not exist, will create new record for patient \"{}\"", patientNumber);
            patientEntity = patientUpdate.toPatientEntity(patientNumber);
        }

        return patientEntity;
    }
}
