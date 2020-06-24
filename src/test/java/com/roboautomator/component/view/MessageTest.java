package com.roboautomator.component.view;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MessageTest {

    @Test
    void builderShouldContainCorrelationIdAndMessageInToString() {
        var correlationId = UUID.randomUUID();
        var message = "Some Message";

        var builder = Message.builder()
            .correlationId(correlationId)
            .messageBody(message);

        assertThat(builder.toString()).contains(correlationId.toString());
        assertThat(builder.toString()).contains(message);
    }
}
