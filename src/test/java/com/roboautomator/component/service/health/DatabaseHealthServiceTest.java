package com.roboautomator.component.service.health;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.roboautomator.component.model.HealthEntity;
import com.roboautomator.component.repository.HealthRepository;
import com.roboautomator.component.util.AbstractLoggingTest;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DatabaseHealthServiceTest extends AbstractLoggingTest<DatabaseHealthService> {

    @Mock
    private HealthRepository healthRepository;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        setupLoggingAppender(new DatabaseHealthService(healthRepository, entityManager));
    }

    @Test
    void shouldReturnAHealthObject() {
        willReturn(true).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.of(getTestHealthEntity())).given(healthRepository).findById(any());

        var health = new DatabaseHealthService(healthRepository, entityManager).getServiceHealth();

        assertThat(health).isNotNull();
        assertThat(health.isAlive()).isTrue();
        assertThat(health.isWritable()).isTrue();
    }

    @Test
    void shouldShowAliveReadableAndWritableInToString() {
        willReturn(true).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.of(getTestHealthEntity())).given(healthRepository).findById(any());

        var serviceHealthString = new DatabaseHealthService(healthRepository, entityManager).getServiceHealth().toString();

        assertThat(serviceHealthString).contains("alive=true");
        assertThat(serviceHealthString).contains("readable=true");
        assertThat(serviceHealthString).contains("writable=true");
    }

    @Test
    void shouldShowAliveIsFalseWhenEntityManagerReturnsFalse() {
        willReturn(false).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.of(getTestHealthEntity())).given(healthRepository).findById(any());


        var serviceHealthString = new DatabaseHealthService(healthRepository, entityManager).getServiceHealth().toString();

        assertThat(serviceHealthString).contains("alive=false");
        assertThat(serviceHealthString).contains("readable=true");
        assertThat(serviceHealthString).contains("writable=true");
    }

    @Test
    void shouldShowWritableIsFalseWhenHealthRepoSaveReturnsNull() {
        willReturn(true).given(entityManager).isOpen();
        willThrow(IllegalArgumentException.class).given(healthRepository).save(any());
        willReturn(Optional.of(getTestHealthEntity())).given(healthRepository).findById(any());

        var serviceHealthString = new DatabaseHealthService(healthRepository, entityManager).getServiceHealth().toString();

        assertThat(serviceHealthString).contains("alive=true");
        assertThat(serviceHealthString).contains("readable=true");
        assertThat(serviceHealthString).contains("writable=false");
    }

    @Test
    void shouldShowReadableIsFalseWhenHealthRepoFindByIdReturnsEmpty() {
        willReturn(true).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.empty()).given(healthRepository).findById(any());

        var serviceHealthString = new DatabaseHealthService(healthRepository, entityManager).getServiceHealth().toString();

        assertThat(serviceHealthString).contains("alive=true");
        assertThat(serviceHealthString).contains("readable=false");
        assertThat(serviceHealthString).contains("writable=true");
    }

    @Test
    void shouldLogSavedHealthEntityAfterPersisting() {
        willReturn(true).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.empty()).given(healthRepository).findById(any());

        new DatabaseHealthService(healthRepository, entityManager).getServiceHealth();

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage)
            .contains("SavedHealthEntity {}");
    }

    @Test
    void shouldLogHealthEntityIfSaveFails() {
        willReturn(true).given(entityManager).isOpen();
        willThrow(IllegalArgumentException.class).given(healthRepository).save(any());
        willReturn(Optional.empty()).given(healthRepository).findById(any());

        new DatabaseHealthService(healthRepository, entityManager).getServiceHealth();

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage)
            .contains("Could not save the entity: {}");
    }

    @Test
    void shouldLogWhenSearchingForHealthEntry() {
        willReturn(true).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.empty()).given(healthRepository).findById(any());

        new DatabaseHealthService(healthRepository, entityManager).getServiceHealth();

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage)
            .contains("Searching for Health Entry by ID: {}");
    }

    @Test
    void shouldLogResponseFromQuery() {
        willReturn(true).given(entityManager).isOpen();
        willReturn(getTestHealthEntity()).given(healthRepository).save(any());
        willReturn(Optional.empty()).given(healthRepository).findById(any());

        new DatabaseHealthService(healthRepository, entityManager).getServiceHealth();

        assertThat(getLoggingEventListAppender().list)
            .extracting(ILoggingEvent::getMessage)
            .contains("RetrievedHealthEntity {}");
    }

    private HealthEntity getTestHealthEntity() {
        return HealthEntity.builder()
            .id(UUID.randomUUID())
            .build();
    }
}
