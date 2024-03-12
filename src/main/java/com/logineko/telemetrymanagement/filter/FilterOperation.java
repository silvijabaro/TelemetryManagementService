package com.logineko.telemetrymanagement.filter;

public enum FilterOperation {
    EQUALS("Equals"),
    LESS_THAN("LessThan"),
    GREATER_THAN("GreaterThan"),
    CONTAINS("Contains");

    private final String value;

    FilterOperation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FilterOperation fromString(String value) {
        for (FilterOperation operation : FilterOperation.values()) {
            if (operation.value.equalsIgnoreCase(value)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("No constant with value " + value + " found");
    }

}
