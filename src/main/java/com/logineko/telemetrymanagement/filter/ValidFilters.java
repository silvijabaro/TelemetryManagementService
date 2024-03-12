package com.logineko.telemetrymanagement.filter;

import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class ValidFilters {

    private final List<String> tractorFields;
    private final List<String> combineFields;

    public List<String> getTractorFields() {
        return tractorFields;
    }

    public List<String> getCombineFields() {
        return combineFields;
    }

    private final List<ValidFilter> validFilterList;

    public ValidFilters() {
        tractorFields = getFieldNames(TractorTelemetry.class);
        combineFields = getFieldNames(CombineTelemetry.class);
        validFilterList = generateValidFilters();
    }

    public List<ValidFilter> getValidFilterList() {
        return this.validFilterList;
    }

    private List<String> getFieldNames(Class<?> telemetryClass) {
        Field[] fields = telemetryClass.getDeclaredFields();
        return Arrays.stream(fields)
                .map(Field::getName)
                .toList();
    }

    private List<ValidFilter> generateValidFilters() {
        List<ValidFilter> validFilters = new ArrayList<>();

        validFilters.addAll(generateValidFiltersForType(TractorTelemetry.class, tractorFields));
        validFilters.addAll(generateValidFiltersForType(CombineTelemetry.class, combineFields));

        return validFilters;
    }

    private List<ValidFilter> generateValidFiltersForType(Class<?> telemetryClass, List<String> fields) {
        List<ValidFilter> validFilters = new ArrayList<>();

        for (String field : fields) {
            ValidFilter validFilter = new ValidFilter();
            validFilter.setApiFieldName(field);
            validFilter.setValidOperations(AllowedFiltersByType.getAllowedFiltersForType(getFieldType(telemetryClass, field)));
            validFilters.add(validFilter);
        }

        return validFilters;
    }

    private Class<?> getFieldType(Class<?> telemetryClass, String fieldName) {
        try {
            Field field = telemetryClass.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            log.error("Failed to get field");
            return null;
        }
    }

}
