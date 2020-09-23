package com.roboautomator.component.swagger;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class SwaggerControllerFuncTest {

    @Test
    public void checkThatSwaggerisReachableFromRootPage() {
        given()
                .when()
                .get("http://localhost:8080/")
                .then()
                .statusCode(
                        Matchers.allOf(
                                Matchers.greaterThanOrEqualTo(200),
                                Matchers.lessThan(400)));
    }
}
