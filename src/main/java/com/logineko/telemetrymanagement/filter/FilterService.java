package com.logineko.telemetrymanagement.filter;

import com.logineko.telemetrymanagement.model.dto.DataFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final ValidFilters validFilters;

    public List<String> validateFilters(List<DataFilter> filters) {
        List<String> invalidFields = new ArrayList<>();

        for (DataFilter filter : filters) {
            String field = filter.getField();
            String operation = (filter.getOperation() != null) ? filter.getOperation() : FilterOperation.EQUALS.getValue();

            if (!isValidFilter(validFilters.getValidFilterList(), field, operation)) {
                invalidFields.add(field);
            }
        }

        return invalidFields;
    }

    private boolean isValidFilter(List<ValidFilter> validFilters, String field, String operation) {
        for (ValidFilter validFilter : validFilters) {
            if (validFilter.getApiFieldName().equalsIgnoreCase(field)) {
                return validFilter.getValidOperations().contains(operation);
            }
        }
        return false;
    }
}
