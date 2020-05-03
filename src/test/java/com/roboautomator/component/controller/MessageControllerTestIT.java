package com.roboautomator.component.controller;

import com.roboautomator.component.model.MessageEntity;
import com.roboautomator.component.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTestIT {

    private static final String TEST_MESSAGE = "Hello World!";

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private MessageRepository messageRepository;

    @BeforeEach
    void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/message");
    }

    @Test
    void canSendMessageToMessageQueue() {

        var stringResponseEntity = template.postForEntity(base.toString(),
                TEST_MESSAGE, String.class);

        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stringResponseEntity.getBody()).isEqualTo(TEST_MESSAGE);
    }

    @Test
    void messageShouldBeSavedToDatabaseOnConsumption() {
        template.postForEntity(base.toString(), TEST_MESSAGE, String.class);

        var messages = messageRepository.findAll();

        assertThat(messages)
                .extracting(MessageEntity::getMessage)
                .contains(TEST_MESSAGE);
    }
}
