package com.roboautomator.component.message;

import static com.roboautomator.component.util.StringHelper.cleanString;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private static final String TOPIC_NAME = "Test.Topic";

    private final MessageRepository messageRepository;

    @KafkaListener(topics = TOPIC_NAME)
    public void onMessage(@Payload String payload, @Headers Map<String, Object> headers) {
        var cleanString = cleanString(payload);
        log.info("New message on topic {}: {}", TOPIC_NAME, cleanString);

        var correlationId = extractCorrelationId(headers);
        MDC.put("correlationId", correlationId.toString());

        var messageEntity = MessageEntity.builder()
            .message(cleanString)
            .correlationId(correlationId)
            .build();
        messageRepository.save(messageEntity);
    }

    private UUID extractCorrelationId(Map<String, Object> headers) {
        var correlationId = headers.get(KafkaHeaders.CORRELATION_ID);
        return (correlationId == null)
            ? UUID.randomUUID()
            : UUID.fromString((String) correlationId);
    }
}
