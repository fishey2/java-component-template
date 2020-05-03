package com.roboautomator.component.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageEntityTest {

    @Test
    void shouldBeAbleToBuildNewEntityUsingBuilder() {
        var testId = UUID.randomUUID();
        var testMessage = "test-message";

        var messageEntity = MessageEntity
                .builder()
                .id(testId)
                .message(testMessage)
                .build();

        assertThat(messageEntity.getId()).isEqualTo(testId);
        assertThat(messageEntity.getMessage()).isEqualTo(testMessage);
    }

}
