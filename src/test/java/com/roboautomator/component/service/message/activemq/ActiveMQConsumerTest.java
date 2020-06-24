package com.roboautomator.component.service.message.activemq;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.model.MessageEntity;
import com.roboautomator.component.repository.MessageRepository;
import com.roboautomator.component.service.message.activemq.consumer.ActiveMQConsumer;
import com.roboautomator.component.util.AbstractLoggingTest;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.messaging.MessageHeaders;

@ExtendWith(MockitoExtension.class)
class ActiveMQConsumerTest extends AbstractLoggingTest<ActiveMQConsumer> {

    private static final String UUID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageHeaders mockHeaders;

    @Mock
    private Message mockMessage;

    @Mock
    private Session mockSession;

    @InjectMocks
    private ActiveMQConsumer activeMQConsumer;

    @BeforeEach
    void setUpLoggingTestConfig() {
        setupLoggingAppender(activeMQConsumer);
    }

    @Test
    void shouldSetMDCCorrelationIdWhenSetInMessageHeader() throws JMSException {
        var correlationId = UUID.randomUUID();

        willReturn(correlationId.toString())
            .given(mockMessage)
            .getJMSCorrelationID();

        activeMQConsumer.handleMessage("Some Message", mockHeaders, mockMessage, mockSession);

        assertThat(MDC.get("correlationId")).isEqualTo(correlationId.toString());
    }

    @Test
    void shouldSaveMessageToMessageRepository() {
        willReturn(MessageEntity.builder().build())
            .given(messageRepository)
            .save(any(MessageEntity.class));

        activeMQConsumer.handleMessage("Some Message", mockHeaders, mockMessage, mockSession);

        verify(messageRepository).save(ArgumentMatchers.any(MessageEntity.class));
    }

    @Test
    void shouldLogErrorWhenFailedToGetJmsCorrelationId() throws JMSException {
        willThrow(new JMSException("Failed"))
            .given(mockMessage)
            .getJMSCorrelationID();

        activeMQConsumer.handleMessage("Some Message", mockHeaders, mockMessage, mockSession);

        assertThat(getLoggingEventListAppender().list)
            .flatExtracting(ILoggingEvent::getFormattedMessage)
            .contains("Could not extract JMSCorrelationID, generated new UUID");
    }

    @Test
    void shouldGenerateRandomUUIDForCorrelationIdWhenCorrelationIdIsWrong() throws JMSException {
        var correlationId = "not-a-uuid";

        willReturn(correlationId)
            .given(mockMessage)
            .getJMSCorrelationID();

        activeMQConsumer.handleMessage("Some Message", mockHeaders, mockMessage, mockSession);

        assertThat(MDC.get("correlationId")).isNotEqualTo(correlationId);
        assertThat(MDC.get("correlationId")).matches(UUID_PATTERN);
    }
    @Test
    void shouldGenerateCorrelationIdWhenOneIsNotProvided() {
        activeMQConsumer.handleMessage("Some Message", mockHeaders, mockMessage, mockSession);

        assertThat(MDC.get("correlationId")).matches(UUID_PATTERN);
    }
}
