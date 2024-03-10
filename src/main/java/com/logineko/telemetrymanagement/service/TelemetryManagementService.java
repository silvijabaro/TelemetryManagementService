package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.entity.TractorTelemetry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TelemetryManagementService {

    void importTelemetry(MultipartFile file);

    List<TractorTelemetry> filterTelemetry(String fieldName, Object value, String operator);
}
