package com.roboautomator.component.message;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@Getter
@RequiredArgsConstructor
public class Message {
    private final UUID correlationId;
    private final String messageBody;
}
