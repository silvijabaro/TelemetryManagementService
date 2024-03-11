package com.logineko.telemetrymanagement.filter;

import java.time.LocalDateTime;
import java.util.*;

public class AllowedFiltersByType {
    private static final Map<Class<?>, List<String>> allowedFiltersByType = new HashMap<>();

    static {
        allowedFiltersByType.put(String.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.CONTAINS.getValue()));
        allowedFiltersByType.put(Integer.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.LESS_THAN.getValue(), FilterOperation.GREATER_THAN.getValue()));
        allowedFiltersByType.put(Double.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.LESS_THAN.getValue(), FilterOperation.GREATER_THAN.getValue()));
        allowedFiltersByType.put(Boolean.class, Collections.singletonList(FilterOperation.EQUALS.getValue()));
        allowedFiltersByType.put(LocalDateTime.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.LESS_THAN.getValue(), FilterOperation.GREATER_THAN.getValue()));
    }

    public static List<String> getAllowedFiltersForType(Class<?> type) {
        return allowedFiltersByType.get(type);
    }
}



