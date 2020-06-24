package com.roboautomator.component.config.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.Collections;

@Configuration
public class ActiveMQConfig implements QueueConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    private ActiveMQSslConnectionFactory getSslConnectionFactory() {
        return new ActiveMQSslConnectionFactory();
    }

    /**
     * <p><b>Strategy:</b> If URL contains ssl then it will use {@link ActiveMQSslConnectionFactory} instead of
     * {@link ActiveMQConnectionFactory}.</p>
     *
     * <p><b>Trusted Packages:</b>
     * <ul>
     *     <li>com.roboautomator.component.model</li>
     * </ul></p>
     *
     * @return the {@link ActiveMQConnectionFactory} to be used.
     */
    public ActiveMQConnectionFactory configureActiveMQConnectionFactory() {

        var activeMQConnectionFactory = brokerUrl.contains("ssl")
                ? getSslConnectionFactory()
                : new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(user);
        activeMQConnectionFactory.setPassword(password);

        activeMQConnectionFactory.setTrustedPackages(Collections.singletonList("com.roboautomator.component.model"));

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
        var defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(getConnectionFactory());
        defaultJmsListenerContainerFactory.setConcurrency("1-1");
        return defaultJmsListenerContainerFactory;
    }

    @Override
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(getConnectionFactory());
    }
}