package com.roboautomator.component.model;

import com.roboautomator.component.service.health.Health;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthEntityTest {

    private static final UUID TEST_ID = UUID.randomUUID();
    private static final OffsetDateTime TEST_CREATED_AT = OffsetDateTime.now().plusSeconds(1L);
    private static final OffsetDateTime TEST_UPDATED_AT = OffsetDateTime.now().plusSeconds(2L);

    @Test
    void shouldBeAbleToBuildNewHealthEntityUsingBuilder() {
        var healthEntity = HealthEntity.builder()
            .id(TEST_ID)
            .createdAt(TEST_CREATED_AT)
            .updatedAt(TEST_UPDATED_AT)
            .build();

        assertThat(healthEntity.getId()).isEqualTo(TEST_ID);
        assertThat(healthEntity.getCreatedAt()).isEqualTo(TEST_CREATED_AT);
        assertThat(healthEntity.getUpdatedAt()).isEqualTo(TEST_UPDATED_AT);
    }

    @Test
    void shouldHaveAllValuesInToStringMethod() {
        var healthEntityString = HealthEntity.builder()
            .id(TEST_ID)
            .createdAt(TEST_CREATED_AT)
            .updatedAt(TEST_UPDATED_AT)
            .build()
            .toString();

        assertThat(healthEntityString).contains("id=" + TEST_ID.toString());
        assertThat(healthEntityString).contains("updatedAt=" + TEST_UPDATED_AT.toString());
        assertThat(healthEntityString).contains("createdAt=" + TEST_CREATED_AT.toString());
    }

    @Test
    void shouldBuildHealthEntity() {
        var healthEntityBuilder = HealthEntity
            .builder()
            .id(TEST_ID)
            .createdAt(TEST_CREATED_AT)
            .updatedAt(TEST_UPDATED_AT);

        assertThat(healthEntityBuilder.toString()).contains("id=" + TEST_ID);
        assertThat(healthEntityBuilder.toString()).contains("updatedAt=" + TEST_UPDATED_AT);
        assertThat(healthEntityBuilder.toString()).contains("createdAt=" + TEST_CREATED_AT);
        assertThat(healthEntityBuilder.build()).isNotNull();
    }
}
