package com.roboautomator.component.service.message;

public interface QueueProducer<T> {

    /**
     * <p>Simplest working solution to be able to send a message to a message queue.</p>
     *
     * @param message the message object (type {@link T}) to send to the message queue.
     */
    void sendMessage(T message);
}
