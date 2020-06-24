package com.roboautomator.component.util;

import com.roboautomator.component.repository.MessageRepository;
import com.roboautomator.component.service.message.activemq.consumer.ActiveMQConsumer;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractMockMvcTest {

    @MockBean
    private ActiveMQConsumer activeMQConsumer;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;
}
