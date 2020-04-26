package com.roboautomator.component.config.message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSslConnectionFactory;
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
        assertThat(activeMQConfig.jmsTemplate().getClass())
                .isEqualTo(JmsTemplate.class);
    }

    @Test
    public void testConnectionFactoryReturnsObject() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getClass())
                .isEqualTo(ActiveMQConnectionFactory.class);
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

    @Test
    public void testSSLConnectionFactoryUsedWhenURLContainsSSL() {
        org.springframework.test.util.ReflectionTestUtils
                .setField(activeMQConfig, "brokerUrl", "tcp+ssl://127.0.0.1:61616");

        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getClass()).isEqualTo(ActiveMQSslConnectionFactory.class);
    }
}
