package com.roboautomator.component.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC_NAME = "Test.Topic";

    private final KafkaTemplate<String, String> producer;

    public void send(String message) {
        log.info("Sending message to Topic {}: {}", TOPIC_NAME, message);

        var record = MessageBuilder
            .withPayload(message)
            .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
            .setHeader(KafkaHeaders.MESSAGE_KEY, MDC.get("correlationId"))
            .setHeader(KafkaHeaders.CORRELATION_ID, MDC.get("correlationId"))
            .build();

        producer.send(record);
    }
}
