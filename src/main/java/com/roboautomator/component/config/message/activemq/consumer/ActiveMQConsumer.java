package com.roboautomator.component.config.message.activemq.consumer;

import com.roboautomator.component.config.message.QueueConsumer;
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
public class ActiveMQConsumer implements QueueConsumer<String> {

    private static Logger log = LoggerFactory.getLogger(ActiveMQConsumer.class);

    private static final String QUEUE_NAME = "testQueue";
    private static final String LOG_SEPARATOR = "- - - - - - - - - - - - - - - - - - - - - - - -";
    @Override
    @JmsListener(destination = QUEUE_NAME)
    public void handleMessage(@Payload String message, @Headers MessageHeaders headers,
                               Message rawMessage, Session session) {

        message = cleanString(message);
        log.info("received <{}>", message);

        log.info(LOG_SEPARATOR);
        log.info("######          Message Details           #####");
        log.info(LOG_SEPARATOR);
        log.info("headers: {}", headers);
        log.info("rawMessage: {}", rawMessage);
        log.info("session: {}", session);
        log.info(LOG_SEPARATOR);
    }
}