package com.roboautomator.component.service.message.activemq.consumer;

import com.roboautomator.component.model.MessageEntity;
import com.roboautomator.component.repository.MessageRepository;
import com.roboautomator.component.service.message.QueueConsumer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;
import javax.jms.Message;

import static com.roboautomator.component.util.StringHelper.cleanString;

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
@Component
@AllArgsConstructor
public class ActiveMQConsumer implements QueueConsumer<String> {

    private static Logger log = LoggerFactory.getLogger(ActiveMQConsumer.class);

    private static final String QUEUE_NAME = "testQueue";
    private static final String LOG_SEPARATOR = "- - - - - - - - - - - - - - - - - - - - - - - -";

    private MessageRepository messageRepository;

    @Override
    @JmsListener(destination = QUEUE_NAME)
    public void handleMessage(@Payload String message, @Headers MessageHeaders headers,
                               Message rawMessage, Session session) {

        var cleanMessage = cleanString(message);

        var messageEntity = MessageEntity.builder()
                .message(cleanMessage)
                .build();

        messageRepository.saveAndFlush(messageEntity);

        log.info("received <{}>", cleanMessage);

        log.info(LOG_SEPARATOR);
        log.info("######          Message Details           #####");
        log.info(LOG_SEPARATOR);
        log.info("headers: {}", headers);
        log.info("rawMessage: {}", rawMessage);
        log.info("session: {}", session);
        log.info(LOG_SEPARATOR);
    }
}