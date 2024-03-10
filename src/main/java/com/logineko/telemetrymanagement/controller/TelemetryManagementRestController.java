package com.logineko.telemetrymanagement.controller;

import com.logineko.telemetrymanagement.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.service.TelemetryManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
public class TelemetryManagementRestController {

    private final TelemetryManagementService telemetryManagementService;

    @PostMapping("/import")
    public ResponseEntity<String> importTelemetry(@RequestParam("file")MultipartFile file) {
        log.info("Importing telemetry data");
        telemetryManagementService.importTelemetry(file);
        return ResponseEntity.ok("Telemetry data successfully imported.");
    }

    @GetMapping("/filter")
    public List<TractorTelemetry> filterTelemetry(
            @RequestParam String fieldName,
            @RequestParam Object value,
            @RequestParam String operator) {
        log.info("Filtering vehicles by "+fieldName+operator+value);
        return telemetryManagementService.filterTelemetry(fieldName, value, operator);
    }


}