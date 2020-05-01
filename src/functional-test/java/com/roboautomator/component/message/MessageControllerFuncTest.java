package com.roboautomator.component.message;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MessageControllerFuncTest {

    @Test
    public void checkMessageEndpointPostRequestReturns200() {
        given()
                .body("Hello")
                .when()
                .post("http://localhost:8080/message")
                .then()
                .statusCode(200);
    }
}
