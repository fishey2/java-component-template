package com.roboautomator.component.util;

import com.roboautomator.component.repository.HealthRepository;
import com.roboautomator.component.service.message.activemq.consumer.ActiveMQConsumer;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractMockMvcTest {

    @MockBean
    private ActiveMQConsumer activeMQConsumer; // Required because of Use of Repository

    @MockBean
    private HealthRepository healthRepository;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;

}
