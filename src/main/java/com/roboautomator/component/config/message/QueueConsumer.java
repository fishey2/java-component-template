package com.roboautomator.component.config.message;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import javax.jms.Message;
import javax.jms.Session;

public interface QueueConsumer<T> {

    /**
     * <p>A listener that listens to the queueName for messages. When a onMessage event is
     * emitted. It will only log the {@link Payload}, {@link Headers}, {@link Message} and {@link Session}.</p>
     *
     * <p>Requires the annotation {@link org.springframework.jms.annotation.JmsListener} to be configured with the
     * queue name</p>
     *
     * @param message    - The de-serialized message of type {@link T}
     * @param headers    - The message {@link Headers} from the message queue.
     * @param rawMessage - The raw message ({@link Message}), unprocessed from the queue.
     * @param session    - The session information ({@link Session})
     */
    void handleMessage(@Payload T message, @Headers MessageHeaders headers,
                               Message rawMessage, Session session);
}
