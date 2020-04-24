package com.roboautomator.component.config.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ActiveMQConfigTestIT {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Autowired
    private ActiveMQConfig activeMQConfig;

    @Test
    public void testJmsTemplateReturnsJmsTemplate() {
        assertThat(activeMQConfig.jmsTemplate().getClass().getSimpleName())
                .isEqualTo(JmsTemplate.class.getSimpleName());
    }

    @Test
    public void testConnectionFactoryReturnsObject() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getClass().getSimpleName())
                .isEqualTo(ActiveMQConnectionFactory.class.getSimpleName());
    }

    @Test
    public void testConnectionFactoryCallsActiveMQWithBrokerUrl() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getBrokerURL())
                .isEqualTo(brokerUrl);
    }

    @Test
    public void testConnectionFactoryCallsActiveMQWithUser() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getUserName())
                .isEqualTo(user);
    }

    @Test
    public void testConnectionFactoryCallsActiveMQWithPassword() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getPassword())
                .isEqualTo(password);
    }
}
