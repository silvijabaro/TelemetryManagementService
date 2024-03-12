package com.logineko.telemetrymanagement.mapper.converter;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.math.NumberUtils;

public class IntegerConverter extends AbstractBeanField {
    @Override
    protected Integer convert(String value) {
        return NumberUtils.isParsable(value) ? Integer.parseInt(value.trim()) : 0;
    }
}
