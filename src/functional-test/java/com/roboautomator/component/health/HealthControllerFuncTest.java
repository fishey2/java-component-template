package com.roboautomator.component.health;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class HealthControllerFuncTest {

    @Test
    public void checkHealthReturns200() {
        given()
                .when()
                .get("http://localhost:8080/health")
                .then()
                .statusCode(200);
    }
}
