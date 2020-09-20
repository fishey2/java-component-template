package com.roboautomator.component.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verify;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.util.AbstractLoggingTest;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.kafka.support.KafkaHeaders;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest extends AbstractLoggingTest<KafkaConsumer> {

    @Mock
    private MessageRepository messageRepository;

    @Captor
    private ArgumentCaptor<MessageEntity> argumentCaptor;

    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    void setup() {
        kafkaConsumer = new KafkaConsumer(messageRepository);
        setupLoggingAppender(kafkaConsumer);
    }

    @Test
    void shouldSaveMessageToMessageRepository() {
        var payload = "Hello Payload";
        var correlationId = UUID.randomUUID();

        kafkaConsumer.onMessage(payload, Map.of(KafkaHeaders.CORRELATION_ID, correlationId.toString()));

        verify(messageRepository).save(argumentCaptor.capture());
        var messageEntity = argumentCaptor.getValue();
        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity.getMessage()).isEqualTo(payload);
        assertThat(messageEntity.getCorrelationId()).isEqualTo(correlationId);
    }

    @Test
    void shouldCreateUUIDValueIfReceived() {
        var payload = "Hello Payload";

        kafkaConsumer.onMessage(payload, Collections.emptyMap());

        verify(messageRepository).save(argumentCaptor.capture());

        var messageEntity = argumentCaptor.getValue();
        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity.getMessage()).isEqualTo(payload);
        assertThat(messageEntity.getCorrelationId()).isNotNull();
    }

    @Test
    void shouldLogWhenMessageReceived() {
        var payload = "Hello Payload";
        var correlationId = UUID.randomUUID();

        kafkaConsumer.onMessage(payload, Map.of(KafkaHeaders.CORRELATION_ID, correlationId.toString()));

        assertThat(MDC.get("correlationId")).isEqualTo(correlationId.toString());
        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
            .containsExactly(tuple("New message on topic {}: {}", Level.INFO));
    }
}
