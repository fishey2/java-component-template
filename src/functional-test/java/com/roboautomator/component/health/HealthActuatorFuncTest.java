package com.roboautomator.component.health;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Test;

public class HealthActuatorFuncTest {

    private static final String HEALTH_PROBE_ENDPOINT = "http://localhost:8081/actuator/health";
    private static final String INFO_PROBE_ENDPOINT = "http://localhost:8081/actuator/info";

    @Test
    void shouldReturnOkWhenRunning() {
        given()
            .when()
            .get(HEALTH_PROBE_ENDPOINT)
            .then()
            .statusCode(200)
            .body("status", equalTo("UP"));
    }

    @Test
    void shouldReturn404ForInfoEndpoint() {
        given().when()
            .get(INFO_PROBE_ENDPOINT)
            .then()
            .statusCode(404);
    }

    @Test
    void shouldReturnDatabaseStatusWhenRunning() {
        given()
            .when()
            .get(HEALTH_PROBE_ENDPOINT)
            .then()
            .statusCode(200)
            .body("components.db.status", equalTo("UP"))
            .body("components.db.details.database", equalTo("PostgreSQL"))
            .body("components.db.details.validationQuery", equalTo("isValid()"));
    }

    @Test
    void shouldReturnDiskSpaceStatusWhenRunning() {
        given()
            .when()
            .get(HEALTH_PROBE_ENDPOINT)
            .then()
            .statusCode(200)
            .body("components.diskSpace.status", equalTo("UP"))
            .body("components.diskSpace.details.total", any(Number.class))
            .body("components.diskSpace.details.free", any(Number.class))
            .body("components.diskSpace.details.threshold", any(Number.class));
    }

    @Test
    void shouldReturnJMSStatusWhenRunning() {
        given()
            .when()
            .get(HEALTH_PROBE_ENDPOINT)
            .then()
            .statusCode(200)
            .body("components.jms.status", equalTo("UP"))
            .body("components.jms.details.provider", equalTo("ActiveMQ"));
    }

    @Test
    void shouldReturnPingStatusWhenRunning() {
        given()
            .when()
            .get(HEALTH_PROBE_ENDPOINT)
            .then()
            .statusCode(200)
            .body("components.ping.status", equalTo("UP"));
    }
}
