package com.roboautomator.component.message;

import io.restassured.http.Header;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;

public class MessageControllerFuncTest {

    private static final String X_CORRELATION_ID = "X-Correlation-Id";
    private static final String CONTENT_TYPE = "Content-Type";

    private static final String MESSAGE_URL = "http://localhost:8080/message";

    @Test
    public void checkMessageEndpointPostRequestReturns200() {
        var contentType = new Header(CONTENT_TYPE, "application/json");

        given()
                .header(contentType)
                .body("{ \"messageBody\": \"Hello\" }")
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
        var correlationId = new Header(X_CORRELATION_ID, "correlation-id");
        var contentType = new Header(CONTENT_TYPE, "application/json");

        given()
                .header(correlationId)
                .header(contentType)
                .body("Hello")
                .when()
                .post(MESSAGE_URL)
                .then()
                .header(X_CORRELATION_ID, "correlation-id");
    }

    @Test
    public void retrieveSameMessageAsSent() {

        var correlationId = UUID.randomUUID();
        var correlationHeader = new Header(X_CORRELATION_ID, correlationId.toString());
        var contentType = new Header(CONTENT_TYPE, "application/json");
        var messageSent = "{ \"messageBody\": \"Hello - retrieveSameMessageAsSent\" }";

        given()
            .header(correlationHeader)
            .header(contentType)
            .body(messageSent)
            .when()
            .post(MESSAGE_URL)
            .then()
            .header(X_CORRELATION_ID, correlationId.toString());

        var expectedResponse = Message.builder()
            .messageBody("Hello - retrieveSameMessageAsSent")
            .correlationId(correlationId)
            .build();

        given()
            .header(correlationHeader)
            .get(MESSAGE_URL + "/" + correlationId.toString())
            .then()
            .body("messageBody", equalTo(expectedResponse.getMessageBody()))
            .body("correlationId", equalTo(expectedResponse.getCorrelationId().toString()));


    }
}
