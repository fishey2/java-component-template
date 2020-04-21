package com.roboautomator.component.config.message.activemq.producer;

import com.roboautomator.component.config.message.activemq.ActiveMQConfig;
import com.roboautomator.component.config.message.QueueProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * {@link com.roboautomator.component.controller.MessageController}</p>
 */
@Component
public class ActiveMQProducer implements QueueProducer<String> {

    private static Logger log = LoggerFactory.getLogger(ActiveMQProducer.class);

    private static final String QUEUE_NAME = "testQueue";

    private final JmsTemplate jmsTemplate;

    public ActiveMQProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendMessage(String message) {
        log.info("Sending message \"{}\" to the \"{}\" queue", message, QUEUE_NAME);
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
    }
}