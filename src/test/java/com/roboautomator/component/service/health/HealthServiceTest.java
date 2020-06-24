package com.roboautomator.component.service.health;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.util.AbstractLoggingTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
class HealthServiceTest extends AbstractLoggingTest<HealthService> {

    @Mock
    private DatabaseHealthService databaseHealthService;

    @BeforeEach
    void setup() {
        setupLoggingAppender(new HealthService(databaseHealthService));
    }

    @Test
    void shouldReturnHealthIsOkIfDatabaseHealthIsOk() {
        var health = Health.builder()
            .alive(true)
            .readable(true)
            .writable(true)
            .build();

        willReturn(health).given(databaseHealthService).getServiceHealth();

        var healthService = new HealthService(databaseHealthService);

        assertThat(healthService.isHealthOk()).isTrue();
    }

    @Test
    void shouldFailIfDatabaseHealthIsNotAlive() {
        var health = Health.builder()
            .alive(false)
            .readable(true)
            .writable(true)
            .build();

        willReturn(health).given(databaseHealthService).getServiceHealth();

        var healthService = new HealthService(databaseHealthService);

        assertThat(healthService.isHealthOk()).isFalse();
    }

    @Test
    void shouldFailIfDatabaseHealthIsNotReadable() {
        var health = Health.builder()
            .alive(true)
            .readable(false)
            .writable(true)
            .build();

        willReturn(health).given(databaseHealthService).getServiceHealth();

        var healthService = new HealthService(databaseHealthService);

        assertThat(healthService.isHealthOk()).isFalse();
    }

    @Test
    void shouldFailIfDatabaseHealthIsNotWritable() {
        var health = Health.builder()
            .alive(true)
            .readable(true)
            .writable(false)
            .build();

        willReturn(health).given(databaseHealthService).getServiceHealth();

        var healthService = new HealthService(databaseHealthService);

        assertThat(healthService.isHealthOk()).isFalse();
    }

    @Test
    void shouldOutputToStringTheDatabaseHealth() {
        var health = Health.builder()
            .alive(true)
            .readable(true)
            .writable(false)
            .build();

        willReturn(health).given(databaseHealthService).getServiceHealth();

        var healthService = new HealthService(databaseHealthService);

        healthService.isHealthOk();

        assertThat(healthService.toString()).contains(health.toString());
        assertThat(healthService.toString()).doesNotContain("databaseHealthService=databaseHealthService");
    }

    @Test
    void shouldLogTheServiceHealth() {
        var health = Health.builder()
            .alive(true)
            .readable(true)
            .writable(false)
            .build();

        willReturn(health).given(databaseHealthService).getServiceHealth();

        var healthService = new HealthService(databaseHealthService);

        healthService.isHealthOk();

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getFormattedMessage)
            .contains("Database Health: Health(alive=true, writable=false, readable=true)");
    }
}
