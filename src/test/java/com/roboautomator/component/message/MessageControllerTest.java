package com.roboautomator.component.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.util.AbstractLoggingTest;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MessageController.class)
@AutoConfigureMockMvc
class MessageControllerTest extends AbstractLoggingTest<MessageController> {

    private static final String TEST_ENDPOINT = "/message";

    private static final String VALID_JSON = "{" +
        "\"messageBody\": \"Hello Message\"" +
        "}";

    @MockBean
    private KafkaProducer kafkaProducer;

    @MockBean
    private MessageRepository messageRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        var messageController = new MessageController(messageRepository, kafkaProducer);
        setupLoggingAppender(messageController);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void shouldCallLogWithMessage() throws Exception {
        willDoNothing().given(kafkaProducer).send(any());

        mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON))
            .andExpect(status().isOk());

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage)
            .contains("Received request to send the message \"{}\" to queue");

        var listOfArgs = new String[] {"Hello Message"};

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getArgumentArray)
            .contains(listOfArgs);
    }

    @Test
    void shouldReturnOkWhenMessageSent() throws Exception {
        willDoNothing().given(kafkaProducer).send(any());

        mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnMessageInResponseBody() throws Exception {
        willDoNothing().given(kafkaProducer).send(any());

        var result = mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON))
            .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello Message");
    }

    @Test
    void shouldReturn404NotFoundIfCorrelationIdNotFound() throws Exception {
        var correlationId = UUID.randomUUID();

        willReturn(Optional.empty())
            .given(messageRepository)
            .findAllByCorrelationId(correlationId);

        mockMvc
            .perform(get(TEST_ENDPOINT + "/" + correlationId))
            .andExpect(status().isNotFound());
    }
}
