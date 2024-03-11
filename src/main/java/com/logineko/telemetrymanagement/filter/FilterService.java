package com.logineko.telemetrymanagement.filter;

import com.logineko.telemetrymanagement.model.dto.DataFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.logineko.telemetrymanagement.filter.ValidFilters.validFilterList;

@Service
public class FilterService {
    public List<String> validateFilters(List<DataFilter> filters) {
        List<String> invalidFields = new ArrayList<>();

        for (DataFilter filter : filters) {
            String field = filter.getField();
            String operation = filter.getOperation();

            if (!isValidFilter(validFilterList, field, operation)) {
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
