package com.roboautomator.component.message;

import com.roboautomator.component.view.Message;
import io.restassured.http.Header;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

public class MessageControllerFuncTest {

    private static final String X_CORRELATION_ID = "X-Correlation-Id";
    private static final String MESSAGE_URL = "http://localhost:8080/message";

    @Test
    public void checkMessageEndpointPostRequestReturns200() {
        given()
                .body("Hello")
                .when()
                .post(MESSAGE_URL)
                .then()
                .statusCode(200);
    }

    @Test
    public void returnsGeneratedCorrelationId() {
        given()
                .body("Hello")
                .when()
                .post(MESSAGE_URL)
                .then()
                .header(X_CORRELATION_ID, any(String.class));
    }

    @Test
    public void returnsTheSameCorrelationIdAsSent() {
        Header header = new Header(X_CORRELATION_ID, "correlation-id");

        given()
                .header(header)
                .body("Hello")
                .when()
                .post(MESSAGE_URL)
                .then()
                .header(X_CORRELATION_ID, "correlation-id");
    }

    @Test
    public void retrieveSameMessageAsSent() {

        var correlationId = UUID.randomUUID();
        Header header = new Header(X_CORRELATION_ID, correlationId.toString());

        var messageSent = "Hello - retrieveSameMessageAsSent";

        given()
            .header(header)
            .body(messageSent)
            .when()
            .post(MESSAGE_URL)
            .then()
            .header(X_CORRELATION_ID, correlationId.toString());

        var expectedResponse = Message.builder()
            .messageBody(messageSent)
            .correlationId(correlationId)
            .build();

        given()
            .header(header)
            .get(MESSAGE_URL + "/" + correlationId.toString())
            .then()
            .body("messageBody", equalTo(expectedResponse.getMessageBody()))
            .body("correlationId", equalTo(expectedResponse.getCorrelationId().toString()));


    }
}
