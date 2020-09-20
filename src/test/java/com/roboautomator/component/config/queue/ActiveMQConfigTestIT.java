package com.roboautomator.component.config.queue;

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
class ActiveMQConfigTestIT {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Autowired
    private ActiveMQConfig activeMQConfig;

    @Test
    void testJmsTemplateReturnsJmsTemplate() {
        assertThat(activeMQConfig.jmsTemplate().getClass())
                .isEqualTo(JmsTemplate.class);
    }

    @Test
    void testConnectionFactoryReturnsObject() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getClass())
                .isEqualTo(ActiveMQConnectionFactory.class);
    }

    @Test
    void testConnectionFactoryCallsActiveMQWithBrokerUrl() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getBrokerURL())
                .isEqualTo(brokerUrl);
    }

    @Test
    void testConnectionFactoryCallsActiveMQWithUser() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getUserName())
                .isEqualTo(user);
    }

    @Test
    void testConnectionFactoryCallsActiveMQWithPassword() {
        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getPassword())
                .isEqualTo(password);
    }

    @Test
    void testSSLConnectionFactoryUsedWhenURLContainsSSL() {
        org.springframework.test.util.ReflectionTestUtils
                .setField(activeMQConfig, "brokerUrl", "tcp+ssl://127.0.0.1:61616");

        assertThat(activeMQConfig.configureActiveMQConnectionFactory().getClass()).isEqualTo(ActiveMQSslConnectionFactory.class);
    }
}
