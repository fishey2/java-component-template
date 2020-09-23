package com.roboautomator.component.message;

import static org.assertj.core.api.Assertions.assertThat;
import com.roboautomator.component.message.Message;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserMessageTest {

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
