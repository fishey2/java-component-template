package com.roboautomator.component.service.message.activemq;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.service.message.activemq.producer.ActiveMQProducer;
import com.roboautomator.component.util.AbstractLoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ActiveMQProducer.class)
public class ActiveMQProducerTest extends AbstractLoggingTest<ActiveMQProducer> {

    @MockBean
    private JmsTemplate jmsTemplate;

    private ActiveMQProducer activeMQProducer;

    @BeforeEach
    void setupProducer() {
        activeMQProducer = new ActiveMQProducer(jmsTemplate);
        setupLoggingAppender(activeMQProducer);
    }

    @Test
    void shouldCallJmsTemplateWithMessageToBeSent() {
        activeMQProducer.sendMessage("Message");

        verify(jmsTemplate, times(1))
                .convertAndSend(anyString(), eq("Message"));
    }

    @Test
    void shouldCallJmsTemplateWithCorrectQueueName() {
        activeMQProducer.sendMessage("Any");

        verify(jmsTemplate, times(1))
                .convertAndSend(eq("testQueue"), anyString());
    }

    @Test
    void shouldCallLogWithMessageInformation() {
        activeMQProducer.sendMessage("Message");

        assertThat(getLoggingEventListAppender().list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .contains("Sending message \"Message\" to the \"testQueue\" queue");
    }
}
