package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.exception.CSVMappingException;
import com.logineko.telemetrymanagement.exception.UnsupportedVehicleException;
import com.logineko.telemetrymanagement.mapper.CSVMapper;
import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.repository.CombineTelemetryRepository;
import com.logineko.telemetrymanagement.repository.TractorTelemetryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class TelemetryManagementServiceImplTest {

    @Mock
    private TractorTelemetryRepository tractorTelemetryRepository;

    @Mock
    private CombineTelemetryRepository combineTelemetryRepository;

    @Mock
    private CSVMapper csvMapper;

    @InjectMocks
    private TelemetryManagementServiceImpl telemetryService;

    @Test
    void importTelemetry_shouldImportTractorTelemetry() throws Exception {
        MultipartFile file = new MockMultipartFile(
                "file", "LD_A_sample.csv", "text/csv", "Sample content".getBytes());

        when(csvMapper.CSVToTractor(file)).thenReturn(Collections.singletonList(new TractorTelemetry()));

        telemetryService.importTelemetry(file, "LD_A_sample.csv");

        verify(tractorTelemetryRepository, times(1)).saveAll(any());
    }

    @Test
    void importTelemetry_shouldImportCombineTelemetry() throws Exception {
        MultipartFile file = new MockMultipartFile(
                "file", "LD_C_sample.csv", "text/csv", "Sample content".getBytes());

        when(csvMapper.CSVToCombine(file)).thenReturn(Collections.singletonList(new CombineTelemetry()));

        telemetryService.importTelemetry(file, "LD_C_sample.csv");

        verify(combineTelemetryRepository, times(1)).saveAll(any());
    }

    @Test
    void importTelemetry_shouldThrowCSVMappingException() throws Exception {
        MultipartFile file = new MockMultipartFile(
                "file", "LD_A_sample.csv", "text/csv", "Sample content".getBytes());

        when(csvMapper.CSVToTractor(file)).thenThrow(new RuntimeException("Mapping failed"));

        assertThrows(CSVMappingException.class, () -> telemetryService.importTelemetry(file, "LD_A_sample.csv"));
    }

    @Test
    void importTelemetry_shouldThrowUnsupportedVehicleException() throws Exception {
        MultipartFile file = new MockMultipartFile(
                "file", "LD_X_sample.csv", "text/csv", "Sample content".getBytes());

        // Mock CSV mapper behavior
        when(csvMapper.CSVToTractor(any())).thenReturn(null);

        // Mock repository behavior
        when(tractorTelemetryRepository.saveAll(any())).thenReturn(null);

        assertThrows(UnsupportedVehicleException.class, () -> telemetryService.importTelemetry(file, "LD_X_sample.csv"));
    }

}

