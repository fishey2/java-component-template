package com.roboautomator.component.controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.roboautomator.component.model.MessageEntity;
import com.roboautomator.component.repository.MessageRepository;
import com.roboautomator.component.view.Message;
import java.net.URL;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerTestIT {

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

    @Test
    void shouldStoreMessageInDatabaseWithCorrectCorrelationId() {
        var correlationId = UUID.randomUUID();

        var headers = new HttpHeaders();
        headers.set("X-Correlation-Id", correlationId.toString());

        var httpEntity = new HttpEntity<>(TEST_MESSAGE, headers);

        template.postForEntity(base.toString(), httpEntity, String.class);
        var message = messageRepository.findAllByCorrelationId(correlationId);

        assertThat(message).isPresent();
        assertThat(message.get().getMessage()).isEqualTo(TEST_MESSAGE);
        assertThat(message.get().getCorrelationId()).isEqualTo(correlationId);
    }

    @Test
    void shouldGetMessageFromDatabaseWithCorrectCorrelationId() {
        var correlationId = UUID.randomUUID();

        var headers = new HttpHeaders();
        headers.set("X-Correlation-Id", correlationId.toString());

        var httpEntity = new HttpEntity<>(TEST_MESSAGE, headers);

        template.postForEntity(base.toString(), httpEntity, String.class);

        var uri = base.toString() + "/" + correlationId;
        System.out.println(uri);

        var responseBody = template.getForEntity(uri, Message.class).getBody();

        System.out.println(responseBody);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getCorrelationId()).isEqualTo(correlationId);
        assertThat(responseBody.getMessageBody()).isEqualTo(TEST_MESSAGE);
    }

    @Test
    void shouldReturn404NotFoundIfCorrelationIdDoesNotExist() {
        var correlationId = UUID.randomUUID();

        var uri = base.toString() + "/" + correlationId;

        var response = template.getForEntity(uri, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
