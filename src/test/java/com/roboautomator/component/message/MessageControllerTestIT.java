package com.roboautomator.component.message;

import static org.assertj.core.api.Assertions.assertThat;
import com.roboautomator.component.message.MessageEntity;
import com.roboautomator.component.message.MessageRepository;
import com.roboautomator.component.message.Message;
import java.net.URL;
import java.util.UUID;
import javax.servlet.http.HttpSessionEvent;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MessageControllerTestIT {

    private static final String TEST_MESSAGE = "{" +
        "\"messageBody\": \"Hello World!\"" +
        "}";

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
            getHttpEntityForBody(TEST_MESSAGE, new HttpHeaders()), String.class);

        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stringResponseEntity.getBody()).isEqualTo("Hello World!");
    }

    @Test
    void messageShouldBeSavedToDatabaseOnConsumption() {
        template.postForEntity(base.toString(),
            getHttpEntityForBody(TEST_MESSAGE, new HttpHeaders()), String.class);

        var messages = messageRepository.findAll();

        assertThat(messages)
            .extracting(MessageEntity::getMessage)
            .contains("Hello World!");
    }

    @Test
    void shouldGetMessageFromDatabaseWithCorrectCorrelationId() {
        var correlationId = UUID.randomUUID();

        var headers = new HttpHeaders();
        headers.set("X-Correlation-Id", correlationId.toString());

        template.postForEntity(base.toString(), getHttpEntityForBody(TEST_MESSAGE, headers), String.class);

        var uri = base.toString() + "/" + correlationId;
        System.out.println(uri);

        var responseBody = template.getForEntity(uri, Message.class).getBody();

        System.out.println(responseBody);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getCorrelationId()).isEqualTo(correlationId);
        assertThat(responseBody.getMessageBody()).isEqualTo("Hello World!");
    }

    @Test
    void shouldReturn404NotFoundIfCorrelationIdDoesNotExist() {
        var correlationId = UUID.randomUUID();

        var uri = base.toString() + "/" + correlationId;

        var response = template.getForEntity(uri, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private HttpEntity<String> getHttpEntityForBody(String body, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(body, headers);
    }
}
