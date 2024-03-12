package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.exception.CSVMappingException;
import com.logineko.telemetrymanagement.exception.UnsupportedVehicleException;
import com.logineko.telemetrymanagement.filter.ValidFilters;
import com.logineko.telemetrymanagement.mapper.CSVMapper;
import com.logineko.telemetrymanagement.model.dto.DataFilter;
import com.logineko.telemetrymanagement.model.dto.FilteredTelemetryResponse;
import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.repository.CombineTelemetryRepository;
import com.logineko.telemetrymanagement.repository.TractorTelemetryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Mock
    private ValidFilters validFilters;

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

        when(csvMapper.CSVToTractor(any())).thenReturn(null);

        when(tractorTelemetryRepository.saveAll(any())).thenReturn(null);

        assertThrows(UnsupportedVehicleException.class, () -> telemetryService.importTelemetry(file, "LD_X_sample.csv"));
    }

    @Test
    void filterTelemetry_shouldReturnFilteredTelemetryResponse() {
        List<DataFilter> dataFilter = Arrays.asList(
                new DataFilter("Field1", "Equals", "Value1"),
                new DataFilter("Field2", "GreaterThan", 42)
        );

        when(validFilters.getTractorFields()).thenReturn(Arrays.asList("field1", "field2"));
        when(validFilters.getCombineFields()).thenReturn(Arrays.asList("field1", "field2"));
        when(tractorTelemetryRepository.findAll((Specification<TractorTelemetry>) any())).thenReturn(Collections.singletonList(new TractorTelemetry()));
        when(combineTelemetryRepository.findAll((Specification<CombineTelemetry>) any())).thenReturn(Collections.singletonList(new CombineTelemetry()));

        FilteredTelemetryResponse result = telemetryService.filterTelemetry(dataFilter);

        verify(tractorTelemetryRepository, times(1)).findAll((Specification<TractorTelemetry>) any());
        verify(combineTelemetryRepository, times(1)).findAll((Specification<CombineTelemetry>) any());

        assertNotNull(result);
    }


    @Test
    void filterTelemetry_shouldHandleEmptyFilter() {
        FilteredTelemetryResponse result = telemetryService.filterTelemetry(Collections.emptyList());
        verifyNoInteractions(tractorTelemetryRepository, combineTelemetryRepository);
        assertNotNull(result);
    }

}

