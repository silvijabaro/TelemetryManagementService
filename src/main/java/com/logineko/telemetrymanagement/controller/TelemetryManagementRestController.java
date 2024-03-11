package com.logineko.telemetrymanagement.controller;

import com.logineko.telemetrymanagement.filter.FilterService;
import com.logineko.telemetrymanagement.model.dto.DataFilter;
import com.logineko.telemetrymanagement.service.TelemetryManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
public class TelemetryManagementRestController {

    private static final String CSV_EXTENSION = "csv";
    private final TelemetryManagementService telemetryManagementService;
    private final FilterService filterService;


    @PostMapping("/import")
    public ResponseEntity<?> importTelemetry(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No CSV file provided.");
        }
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!CSV_EXTENSION.equals(fileExtension.toLowerCase())) {
            return ResponseEntity.badRequest().body("Invalid file extension. Expecting CSV file.");
        }
        log.info("Importing telemetry data");
        telemetryManagementService.importTelemetry(file, file.getOriginalFilename());
        return ResponseEntity.ok("Telemetry data successfully imported.");
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterTelemetry(
            @RequestBody @Valid List<DataFilter> filter) {
        List<String> invalidFields = filterService.validateFilters(filter);
        if (invalidFields.isEmpty()) {
            log.info("Filtering telemetry data");
            return ResponseEntity.ok(telemetryManagementService.filterTelemetry(filter));
        } else {
            return ResponseEntity.badRequest().body("Invalid filters for fields: " + invalidFields);
        }
    }

}