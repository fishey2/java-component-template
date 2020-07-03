package com.roboautomator.component.patient;

import com.roboautomator.component.DefaultExceptionResponse;
import com.roboautomator.component.util.ValidationError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
@NoArgsConstructor
public class PatientExceptionResponse extends DefaultExceptionResponse<ValidationError> {
}
