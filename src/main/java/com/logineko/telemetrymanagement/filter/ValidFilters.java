package com.logineko.telemetrymanagement.filter;

import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ValidFilters {

    private static List<String> tractorFields;
    private static List<String> combineFields;

    public static List<String> tractorFieldsLowerCase;
    public static List<String> combineFieldsLowerCase;
    public static List<ValidFilter> validFilterList;

    static {
        tractorFields = ValidFilters.getFieldNames(TractorTelemetry.class);
        combineFields = ValidFilters.getFieldNames(CombineTelemetry.class);
        tractorFieldsLowerCase = lowerCaseAllFields(tractorFields);
        combineFieldsLowerCase = lowerCaseAllFields(combineFields);
        validFilterList = generateValidFilters();
    }

    private static List<String> getFieldNames(Class<?> telemetryClass) {
        Field[] fields = telemetryClass.getDeclaredFields();
        return Arrays.stream(fields)
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    private static List<String> lowerCaseAllFields(List<String> telemetry) {
        return telemetry.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    private static List<ValidFilter> generateValidFilters() {
        List<ValidFilter> validFilters = new ArrayList<>();

        validFilters.addAll(generateValidFiltersForType(TractorTelemetry.class, tractorFields));
        validFilters.addAll(generateValidFiltersForType(CombineTelemetry.class, combineFields));

        return validFilters;
    }

    private static List<ValidFilter> generateValidFiltersForType(Class<?> telemetryClass, List<String> fields) {
        List<ValidFilter> validFilters = new ArrayList<>();

        for (String field : fields) {
            ValidFilter validFilter = new ValidFilter();
            validFilter.setApiFieldName(field);
            validFilter.setValidOperations(AllowedFiltersByType.getAllowedFiltersForType(getFieldType(telemetryClass, field)));
            validFilters.add(validFilter);
        }

        return validFilters;
    }

    private static Class<?> getFieldType(Class<?> telemetryClass, String fieldName) {
        try {
            Field field = telemetryClass.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            log.error("Failed to get field");
            return null;
        }
    }

}
