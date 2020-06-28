package com.roboautomator.component.message;

import static com.roboautomator.component.util.StringHelper.cleanString;
import com.roboautomator.component.QueueConsumer;
import java.util.UUID;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * <p><b>Simple consumer for ActiveMQ</b></p>
 *
 * <b>See:</b> {@link org.springframework.jms.config.AbstractJmsListenerContainerFactory}
 * for event emitter configuration.
 *
 * <b>Required in config (application.yml):</b>
 * <ul>
 *     <li>spring.activemq.broker-url</li>
 *     <li>spring.activemq.user</li>
 *     <li>spring.activemq.password</li>
 * </ul>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ActiveMQConsumer implements QueueConsumer<String> {

    private static final String QUEUE_NAME = "testQueue";

    private static final String UUID_PATTERN = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";

    private final MessageRepository messageRepository;

    @Override
    @JmsListener(destination = QUEUE_NAME)
    public void handleMessage(@Payload String message, @Headers MessageHeaders headers,
                              Message rawMessage, Session session) {

        var cleanMessage = cleanString(message);

        var correlationId = extractCorrelationId(rawMessage);

        MDC.put("correlationId", correlationId.toString());

        var messageEntity = MessageEntity.builder()
            .message(cleanMessage)
            .correlationId(correlationId)
            .build();

        messageRepository.save(messageEntity);
    }

    private UUID extractCorrelationId(Message jmsMessage) {
        try {
            var correlationId = jmsMessage.getJMSCorrelationID();

            return correlationId != null && correlationId.matches(UUID_PATTERN)
                ? UUID.fromString(correlationId)
                : UUID.randomUUID();

        } catch (JMSException exception) {
            log.info("Could not extract JMSCorrelationID, generated new UUID");
            return UUID.randomUUID();
        }
    }
}