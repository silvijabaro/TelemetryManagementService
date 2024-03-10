package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.repository.TractorTelemetryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TelemetryManagementServiceImpl implements TelemetryManagementService {

    private final TractorTelemetryRepository tractorTelemetryRepository;


    @Override
    public void importTelemetry(MultipartFile file) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TractorTelemetry> filterTelemetry(String fieldName, Object value, String operator) {
        throw new UnsupportedOperationException();
    }

}
