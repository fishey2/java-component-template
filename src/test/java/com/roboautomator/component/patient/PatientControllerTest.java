package com.roboautomator.component.patient;

import static com.roboautomator.component.patient.utils.PatientTestUtils.getPatientNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.jayway.jsonpath.JsonPath;
import com.roboautomator.component.util.AbstractMockMvcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PatientController.class)
@AutoConfigureMockMvc
class PatientControllerTest extends AbstractMockMvcTest {

    private static final String TEST_ENDPOINT = "/patient";
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

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        var patientController = new PatientController(patientRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController)
            .setControllerAdvice(PatientControllerAdvice.class)
            .build();
    }

    @Test
    void shouldReturn200OKWhenCreatingNewPatient() throws Exception {
        var patientNumber = getPatientNumber();
        willReturn(null)
            .given(patientRepository)
            .save(any(PatientEntity.class));

        mockMvc.perform(post(TEST_ENDPOINT + "/" + patientNumber)
            .contentType(MediaType.APPLICATION_JSON)
            .content(VALID_PATIENT_UPDATE))
            .andExpect(status().isOk());

        verify(patientRepository).save(any(PatientEntity.class));
    }

    @Test
    void shouldReturn400WhenPatientNumberIsNotTenNumbers() throws Exception {
        var patientNumber = "123456";

        var response = mockMvc.perform(post(TEST_ENDPOINT + "/" + patientNumber)
            .contentType(MediaType.APPLICATION_JSON)
            .content(VALID_PATIENT_UPDATE))
            .andExpect(status().isBadRequest())
            .andReturn();

        verifyNoInteractions(patientRepository);

        var responseAsString = response.getResponse().getContentAsString();
        System.out.println(responseAsString);
        assertThat(responseAsString).isNotNull();
        assertThat(JsonPath.<String>read(responseAsString, "$.message")).isEqualTo("Validation failed");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].field")).isEqualTo("patientNumber");
        assertThat(JsonPath.<String>read(responseAsString, "$.errors[0].error")).contains("patientNumber");
    }
}
