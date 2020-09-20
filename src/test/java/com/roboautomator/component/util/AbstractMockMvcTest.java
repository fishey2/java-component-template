package com.roboautomator.component.util;

import com.roboautomator.component.message.ActiveMQConsumer;
import com.roboautomator.component.message.MessageRepository;
import com.roboautomator.component.patient.PatientRepository;
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
    protected PatientRepository patientRepository;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;

}
