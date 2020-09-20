package com.roboautomator.component.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.verify;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.util.AbstractLoggingTest;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@ExtendWith(MockitoExtension.class)
class KafkaProducerTest extends AbstractLoggingTest<KafkaProducer> {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Captor
    private ArgumentCaptor<Message<String>> argumentCaptor;

    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setup() {
        kafkaProducer = new KafkaProducer(kafkaTemplate);
        setupLoggingAppender(kafkaProducer);
    }

    @Test
    void shouldSentMessageToKafka() {
        var message = "some message";
        var correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);

        kafkaProducer.send(message);

        verify(kafkaTemplate).send(argumentCaptor.capture());
        var sentMessage = argumentCaptor.getValue();
        assertThat(sentMessage).isNotNull();
        assertThat(sentMessage.getPayload()).isEqualTo(message);
        assertThat(sentMessage.getHeaders()).isNotEmpty();
        assertThat(sentMessage.getHeaders().get(KafkaHeaders.MESSAGE_KEY)).isEqualTo(correlationId);
        assertThat(sentMessage.getHeaders().get(KafkaHeaders.CORRELATION_ID)).isEqualTo(correlationId);
    }

    @Test
    void shouldLogWhenMessageReceived() {
        var payload = "Hello Payload";

        kafkaProducer.send(payload);

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
            .containsExactly(tuple("Sending message to Topic {}: {}", Level.INFO));
    }
}
