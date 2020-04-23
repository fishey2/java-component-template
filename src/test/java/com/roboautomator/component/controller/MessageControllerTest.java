package com.roboautomator.component.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.service.message.activemq.producer.ActiveMQProducer;

import com.roboautomator.component.util.AbstractLoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.jms.MessageNotWriteableException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MessageController.class)
@AutoConfigureMockMvc
public class MessageControllerTest extends AbstractLoggingTest<MessageController> {

    private static final String TEST_ENDPOINT = "/message";

    @MockBean
    private ActiveMQProducer activeMQProducer;

    private MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc() {
        MessageController messageController = new MessageController(activeMQProducer);
        setupLoggingAppender(messageController);
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
    }

    @Test
    public void shouldCallLogWithMessage() throws Exception {
        willDoNothing().given(activeMQProducer).sendMessage(any());

        mockMvc.perform(post(TEST_ENDPOINT).content("Hello"))
                .andExpect(status().isOk());

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getMessage)
                .contains("Received request to send the message \"{}\" to queue");

        String[] listOfArgs = new String[]{"Hello"};

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getArgumentArray)
                .contains(listOfArgs);
    }

    @Test
    public void shouldReturnOkWhenMessageSent() throws Exception {
        willDoNothing().given(activeMQProducer).sendMessage(any());

        mockMvc.perform(post(TEST_ENDPOINT).content("Hello"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnMessageInResponseBody() throws Exception {
        willDoNothing().given(activeMQProducer).sendMessage(any());

        MvcResult result = mockMvc.perform(post(TEST_ENDPOINT).content("Hello"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo("Hello");
    }

    @Test
    public void shouldReturn503WhenMessageNotSent() throws Exception {
        willThrow(new org.springframework.jms.MessageNotWriteableException(new MessageNotWriteableException("Error Occurred")))
                .given(activeMQProducer)
                .sendMessage("error");

        mockMvc.perform(post(TEST_ENDPOINT).content("error"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldReturnErrorMessageWhenMessageHasNotBeenSent() throws Exception {
        Throwable exception = new org.springframework.jms.MessageNotWriteableException(new MessageNotWriteableException("Error Occurred"));
        willThrow(exception)
                .given(activeMQProducer)
                .sendMessage("error");

        MvcResult result = mockMvc.perform(post(TEST_ENDPOINT).content("error"))
                .andReturn();

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(exception.toString());
    }

    @Test
    public void shouldLogAnErrorWhenMessageCanNotBeProcessed() throws Exception {
        Throwable exception = new org.springframework.jms.MessageNotWriteableException(new MessageNotWriteableException("Error Occurred"));
        willThrow(exception)
                .given(activeMQProducer)
                .sendMessage("error");

        mockMvc.perform(post(TEST_ENDPOINT).content("error"));

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getLevel)
                .contains(Level.ERROR);

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Could not process the message \"error\", returning");

    }
}
