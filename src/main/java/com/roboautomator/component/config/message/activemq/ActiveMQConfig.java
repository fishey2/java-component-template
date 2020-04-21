package com.roboautomator.component.config.message.activemq;

import com.roboautomator.component.config.message.QueueConfig;
import com.roboautomator.component.config.message.activemq.consumer.ActiveMQConsumer;
import com.roboautomator.component.config.message.activemq.producer.ActiveMQProducer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig implements QueueConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    public ActiveMQConnectionFactory configureActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(user);
        activeMQConnectionFactory.setPassword(password);

        return activeMQConnectionFactory;
    }

    @Override
    public CachingConnectionFactory getConnectionFactory() {
        return new CachingConnectionFactory(
                configureActiveMQConnectionFactory());
    }

    @Bean
    @Override
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(getConnectionFactory());
        factory.setConcurrency("1-1");
        return factory;
    }

    @Override
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(getConnectionFactory());
    }

    @Override
    public ActiveMQProducer producer() {
        return new ActiveMQProducer(jmsTemplate());
    }

    @Override
    public ActiveMQConsumer consumer() {
        return new ActiveMQConsumer();
    }

}