package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.model.dto.DataFilter;
import com.logineko.telemetrymanagement.model.dto.FilteredTelemetry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TelemetryManagementService {

    void importTelemetry(MultipartFile file, String fileName) throws Exception;

    FilteredTelemetry filterTelemetry(List<DataFilter> filter);
}
