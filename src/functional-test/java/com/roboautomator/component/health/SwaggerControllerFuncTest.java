package com.roboautomator.component.health;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SwaggerControllerFuncTest {

    @Test
    public void makeSureThatGoogleIsUp() {
        given()
                .when()
                .post("http://localhost:8080/")
                .then()
                .statusCode(
                        Matchers.allOf(
                                Matchers.greaterThanOrEqualTo(300),
                                Matchers.lessThan(400)));
    }
}
