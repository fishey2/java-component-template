package com.roboautomator.component.patient;

import static com.roboautomator.component.patient.utils.PatientTestUtils.getPatientNumber;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerTestIT {

    private static final String TEST_TITLE = "title";
    private static final String TEST_FIRST_NAME = "first";
    private static final String TEST_MIDDLE_NAMES = "middle";
    private static final String TEST_LAST_NAME = "last";

    private static final String VALID_PATIENT_UPDATE = "{" +
        " \"title\": \"" + TEST_TITLE + "\"," +
        " \"firstName\": \"" + TEST_FIRST_NAME + "\"," +
        " \"middleNames\": \"" + TEST_MIDDLE_NAMES + "\"," +
        " \"lastName\": \"" + TEST_LAST_NAME + "\" " +
        "}";

    @LocalServerPort
    private int port;

    private URL baseUrl;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private PatientRepository patientRepository;

    @BeforeEach
    void setBaseUrl() throws MalformedURLException {
        baseUrl = URI.create("http://localhost:" + port + "/patient").toURL();
    }

    @Test
    void shouldSaveNewPatientToDatabase() {
        var patientNumber = getPatientNumber();

        var response = template.postForEntity(baseUrl.toString() + "/" + patientNumber,
            getHttpEntity(VALID_PATIENT_UPDATE), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var patient = patientRepository.findByNumber(patientNumber);

        assertThat(patient).isPresent();

        assertThat(patient.get().getNumber()).isEqualTo(patientNumber);
        assertThat(patient.get().getTitle()).isEqualTo(TEST_TITLE);
        assertThat(patient.get().getFirstName()).isEqualTo(TEST_FIRST_NAME);
        assertThat(patient.get().getMiddleNames()).isEqualTo(TEST_MIDDLE_NAMES);
        assertThat(patient.get().getLastName()).isEqualTo(TEST_LAST_NAME);
    }

    @Test
    void shouldUpdateExistingPatientInDatabase() {
        var patientNumber = getPatientNumber();

        var response = template.postForEntity(baseUrl.toString() + "/" + patientNumber,
            getHttpEntity(VALID_PATIENT_UPDATE), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var patient = patientRepository.findByNumber(patientNumber);

        assertThat(patient).isPresent();
        assertThat(patient.get().getLastName()).isEqualTo(TEST_LAST_NAME);

        var updateResponse = template.postForEntity(baseUrl.toString() + "/" + patientNumber,
            getHttpEntity("{" +
                " \"title\": \"" + TEST_TITLE + "\"," +
                " \"firstName\": \"" + TEST_FIRST_NAME + "\"," +
                " \"middleNames\": \"" + TEST_MIDDLE_NAMES + "\"," +
                " \"lastName\": \"SomeoneElse\" " +
                "}"), String.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        patient = patientRepository.findByNumber(patientNumber);

        assertThat(patient).isPresent();

        assertThat(patient.get().getNumber()).isEqualTo(patientNumber);
        assertThat(patient.get().getLastName()).isEqualTo("SomeoneElse");
    }

    private HttpEntity<String> getHttpEntity(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        return new HttpEntity<>(payload, headers);
    }

}

