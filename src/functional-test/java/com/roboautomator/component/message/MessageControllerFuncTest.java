package com.roboautomator.component.message;

import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;

public class MessageControllerFuncTest {

    private static final String X_CORRELATION_ID = "X-Correlation-Id";

    @Test
    public void checkMessageEndpointPostRequestReturns200() {
        given()
                .body("Hello")
                .when()
                .post("http://localhost:8080/message")
                .then()
                .statusCode(200);
    }

    @Test
    public void returnsGeneratedCorrelationId() {
        given()
                .body("Hello")
                .when()
                .post("http://localhost:8080/message")
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
                .post("http://localhost:8080/message")
                .then()
                .header(X_CORRELATION_ID, "correlation-id");
    }
}
