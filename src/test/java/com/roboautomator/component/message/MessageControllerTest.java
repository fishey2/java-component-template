package com.roboautomator.component.message;

import ch.qos.logback.classic.Level;
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

import javax.jms.MessageNotWriteableException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MessageController.class)
@AutoConfigureMockMvc
class MessageControllerTest extends AbstractLoggingTest<MessageController> {

    private static final String TEST_ENDPOINT = "/message";

    private static final String VALID_JSON = "{" +
        "\"messageBody\": \"Hello Message\"" +
        "}";

    private static final String ERROR_JSON = "{" +
        "\"messageBody\": \"error\"" +
        "}";

    @MockBean
    private ActiveMQProducer activeMQProducer;

    @MockBean
    private MessageRepository messageRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setupMockMvc() {
        var messageController = new MessageController(messageRepository, activeMQProducer);
        setupLoggingAppender(messageController);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    void shouldCallLogWithMessage() throws Exception {
        willDoNothing().given(activeMQProducer).sendMessage(any());

        mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON))
            .andExpect(status().isOk());

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getMessage)
                .contains("Received request to send the message \"{}\" to queue");

        var listOfArgs = new String[]{"Hello Message"};

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getArgumentArray)
                .contains(listOfArgs);
    }

    @Test
    void shouldReturnOkWhenMessageSent() throws Exception {
        willDoNothing().given(activeMQProducer).sendMessage(any());

        mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnMessageInResponseBody() throws Exception {
        willDoNothing().given(activeMQProducer).sendMessage(any());

        var result = mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_JSON))
            .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello Message");
    }

    @Test
    void shouldReturn503WhenMessageNotSent() throws Exception {
        willThrow(new org.springframework.jms.MessageNotWriteableException(new MessageNotWriteableException("Error Occurred")))
                .given(activeMQProducer)
                .sendMessage("error");

        mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ERROR_JSON))
            .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldReturnErrorMessageWhenMessageHasNotBeenSent() throws Exception {
        var exception = new org.springframework.jms.MessageNotWriteableException(new MessageNotWriteableException("Error Occurred"));
        willThrow(exception)
                .given(activeMQProducer)
                .sendMessage("error");

        var result = mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ERROR_JSON))
            .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(exception.toString());
    }

    @Test
    void shouldLogAnErrorWhenMessageCanNotBeProcessed() throws Exception {
        var exception = new org.springframework.jms.MessageNotWriteableException(new MessageNotWriteableException("Error Occurred"));
        willThrow(exception)
                .given(activeMQProducer)
                .sendMessage("error");

        mockMvc
            .perform(post(TEST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ERROR_JSON));

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getLevel)
                .contains(Level.ERROR);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Could not process the message \"error\", returning");
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
