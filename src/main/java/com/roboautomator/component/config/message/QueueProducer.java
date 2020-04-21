package com.roboautomator.component.config.message;

import org.springframework.jms.core.JmsTemplate;

public interface QueueProducer<T> {

    /**
     * <p>Simplest working solution to be able to send a message to a message queue.</p>
     *
     * <p>{@link JmsTemplate} has everything required (Headers, etc), which can be
     * overridden if required.</p>
     *
     * @param message the message object (type {@link T}) to send to the message queue.
     */
    void sendMessage(T message);
}
