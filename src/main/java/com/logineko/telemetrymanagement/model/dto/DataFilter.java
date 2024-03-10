package com.logineko.telemetrymanagement.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DataFilter {
    @NotNull(message = "Field cannot be null")

    private String field;

    @Pattern(regexp = "Equals|LessThan|GreaterThan|Contains", message = "Invalid operation")
    private String operation = "Equals"; // If not provided, Equals is the default operation
    @NotNull(message = "Value cannot be null")

    private Object value;
}
