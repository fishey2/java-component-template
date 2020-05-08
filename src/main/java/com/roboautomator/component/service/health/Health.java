package com.roboautomator.component.service.health;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Health {
    private final boolean alive;
    private final boolean writable;
    private final boolean readable;
}
