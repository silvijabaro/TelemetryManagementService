package com.logineko.telemetrymanagement.mapper.util;

import com.opencsv.bean.AbstractBeanField;
import org.apache.commons.lang3.StringUtils;

public class DoubleConverter extends AbstractBeanField {
    @Override
    protected Double convert(String value) {
        return StringUtils.isNumeric(value) ? Double.parseDouble(value.trim()) : 0;
    }
}
