package com.roboautomator.component;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
public abstract class DefaultExceptionResponse<T> {

    private String message;

    private List<T> errors;
}
