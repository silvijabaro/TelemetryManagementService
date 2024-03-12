package com.logineko.telemetrymanagement.mapper.converter;

import com.opencsv.bean.AbstractBeanField;

public class BooleanConverter extends AbstractBeanField {
    @Override
    protected Boolean convert(String value) {
        return "on".equalsIgnoreCase(value) || "active".equalsIgnoreCase(value);
    }
}
