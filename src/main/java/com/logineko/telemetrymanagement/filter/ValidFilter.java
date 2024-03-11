package com.logineko.telemetrymanagement.filter;

import lombok.Data;

import java.util.List;

@Data
public class ValidFilter {
    String apiFieldName;
    List<String> validOperations;
}
