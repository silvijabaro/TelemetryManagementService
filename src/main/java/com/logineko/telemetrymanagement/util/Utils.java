package com.logineko.telemetrymanagement.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static List<String> getLowerCaseFieldNames(Class<?> telemetryClass) {
        Field[] fields = telemetryClass.getDeclaredFields();
        return Arrays.stream(fields)
                .map(field -> field.getName().toLowerCase())
                .collect(Collectors.toList());
    }
}
