package com.roboautomator.component.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MessageEntityTest {

    @Test
    void shouldBeAbleToBuildNewEntityUsingBuilder() {
        var testId = UUID.randomUUID();
        var testMessage = "test-message";
        var createdAt = OffsetDateTime.now();
        var updatedAt = OffsetDateTime.now().plusDays(1L);

        var messageEntity = MessageEntity
                .builder()
                .id(testId)
                .message(testMessage)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        assertThat(messageEntity.getId()).isEqualTo(testId);
        assertThat(messageEntity.getMessage()).isEqualTo(testMessage);
        assertThat(messageEntity.getCreatedAt()).isEqualTo(createdAt);
        assertThat(messageEntity.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void shouldHaveAllValuesInToStringMethod() {
        var testMessage = "test-message";
        var testId = UUID.randomUUID();
        var messageEntity = MessageEntity
                .builder()
                .id(testId)
                .message(testMessage)
                .build();

        assertThat(messageEntity.toString()).contains("id=" + testId);
        assertThat(messageEntity.toString()).contains("message=" + testMessage);
    }

    @Test
    void shouldBuildMessageEntity() {
        var testMessage = "test-message";
        var testId = UUID.randomUUID();
        var messageEntityBuilder = MessageEntity
            .builder()
            .id(testId)
            .message(testMessage);

        assertThat(messageEntityBuilder.toString()).contains("id=" + testId);
        assertThat(messageEntityBuilder.toString()).contains("message=" + testMessage);
        assertThat(messageEntityBuilder.build()).isNotNull();
    }
}
