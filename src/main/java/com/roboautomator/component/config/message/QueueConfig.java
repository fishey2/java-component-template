package com.roboautomator.component.config.message;

import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
public interface QueueConfig {

    /**
     * Required by JmsListener annotation (Assumed to have either DefaultJmsListnenerCobtainerFactory
     * as jmsListenerContainerFactory or JmsListenerContainerFactory)
     *
     * @return {@link DefaultJmsListenerContainerFactory}
     */
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory();

    /**
     * not required to be bean {@link CachingConnectionFactory}.
     *
     * @return the configured {@link CachingConnectionFactory}
     */
    CachingConnectionFactory getConnectionFactory();

    /**
     * Configures the {@link JmsTemplate} to include the {@link CachingConnectionFactory}.
     *
     * @return the configured {@link JmsTemplate}
     */
    JmsTemplate jmsTemplate();

    /**
     * Creates new producer
     * @return the configured {@link QueueProducer}
     */
    QueueProducer<?> producer();

    /**
     * Creates new sexy consumer
     * @return the configured {@link QueueConsumer}
     */
    QueueConsumer<?> consumer();
}

