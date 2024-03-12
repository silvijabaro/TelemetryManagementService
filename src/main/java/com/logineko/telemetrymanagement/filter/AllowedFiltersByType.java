package com.logineko.telemetrymanagement.filter;

import java.time.LocalDateTime;
import java.util.*;

public class AllowedFiltersByType {
    private static final Map<Class<?>, List<String>> allowedFilters = new HashMap<>();

    static {
        allowedFilters.put(String.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.CONTAINS.getValue()));
        allowedFilters.put(Integer.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.LESS_THAN.getValue(), FilterOperation.GREATER_THAN.getValue()));
        allowedFilters.put(Double.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.LESS_THAN.getValue(), FilterOperation.GREATER_THAN.getValue()));
        allowedFilters.put(Boolean.class, Collections.singletonList(FilterOperation.EQUALS.getValue()));
        allowedFilters.put(LocalDateTime.class, Arrays.asList(FilterOperation.EQUALS.getValue(), FilterOperation.LESS_THAN.getValue(), FilterOperation.GREATER_THAN.getValue()));
    }

    private AllowedFiltersByType() {
    }

    public static List<String> getAllowedFiltersForType(Class<?> type) {
        return allowedFilters.get(type);
    }
}



