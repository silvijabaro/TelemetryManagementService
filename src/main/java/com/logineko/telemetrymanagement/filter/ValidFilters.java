package com.logineko.telemetrymanagement.filter;

import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ValidFilters {

    private List<String> tractorFields;
    private List<String> combineFields;

    private List<String> tractorFieldsLowerCase;
    private List<String> combineFieldsLowerCase;
    private List<ValidFilter> validFilterList;

    public ValidFilters() {
        tractorFields = getFieldNames(TractorTelemetry.class);
        combineFields = getFieldNames(CombineTelemetry.class);
        tractorFieldsLowerCase = lowerCaseAllFields(tractorFields);
        combineFieldsLowerCase = lowerCaseAllFields(combineFields);
        validFilterList = generateValidFilters();
    }

    public List<String> getTractorFieldsLowerCase() {
        return tractorFieldsLowerCase;
    }

    public List<String> getCombineFieldsLowerCase() {
        return combineFieldsLowerCase;
    }

    public List<ValidFilter> getValidFilterList() {
        return this.validFilterList;
    }

    private List<String> getFieldNames(Class<?> telemetryClass) {
        Field[] fields = telemetryClass.getDeclaredFields();
        return Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    private List<String> lowerCaseAllFields(List<String> telemetry) {
        return telemetry.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
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
