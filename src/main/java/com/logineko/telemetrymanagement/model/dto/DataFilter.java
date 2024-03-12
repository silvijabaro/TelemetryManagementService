package com.logineko.telemetrymanagement.model.dto;

import com.logineko.telemetrymanagement.filter.FilterOperation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DataFilter {
    @NotNull(message = "Field cannot be null")
    private String field;

    @Pattern(regexp = "Equals|LessThan|GreaterThan|Contains", message = "Invalid operation")
    private String operation;
    @NotNull(message = "Value cannot be null")
    private Object value;

    public DataFilter(String field, String operation, Object value) {
        this.field = field;
        this.operation = (operation != null) ? operation : FilterOperation.EQUALS.getValue();
        this.value = value;
    }
}
