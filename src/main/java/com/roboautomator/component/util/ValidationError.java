package com.roboautomator.component.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ValidationError {
    private final String field;
    private final String error;
}
