package com.roboautomator.component.message;

import com.roboautomator.component.QueueProducer;
import com.roboautomator.component.config.queue.ActiveMQConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * <p><b>Producer for ActiveMQ implementation</b></p>
 *
 * <b>Required in config (application.yml):</b>
 * <ul>
 *     <li>mq.queueName</li>
 *     <li>spring.activemq.broker-url</li>
 *     <li>spring.activemq.user</li>
 *     <li>spring.activemq.password</li>
 * </ul>
 *
 * <b>Usage:</b>
 * <p>The ActiveMQ configuration is setup in
 * {@link ActiveMQConfig} and access to
 * {@link #sendMessage(String)} will be through instantiating this class.</p>
 *
 * <p>An example implementation of usage can be found in
 * {@link MessageController}</p>
 */
@Slf4j
@Component
@AllArgsConstructor
public class ActiveMQProducer implements QueueProducer<String> {

    private static final String QUEUE_NAME = "testQueue";

    private final JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(String message) {
        log.info("Sending message \"{}\" to the \"{}\" queue", message, QUEUE_NAME);
        jmsTemplate.convertAndSend(QUEUE_NAME, message, msg -> {
            msg.setJMSCorrelationID(MDC.get("correlationId"));
            return msg;
        });
    }
}