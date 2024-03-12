package com.logineko.telemetrymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.logineko.telemetrymanagement.filter.FilterService;
import com.logineko.telemetrymanagement.model.dto.DataFilter;
import com.logineko.telemetrymanagement.service.TelemetryManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class TelemetryManagementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TelemetryManagementService telemetryManagementService;

    @MockBean
    private FilterService filterService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void importTelemetry_shouldReturnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "sample.csv", MediaType.TEXT_PLAIN_VALUE, "Sample content".getBytes());

        doNothing().when(telemetryManagementService).importTelemetry(any(), any());

        mockMvc.perform(multipart("/api/v1/telemetry/import")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void importTelemetry_shouldReturnBadRequestWhenNoFile() throws Exception {
        mockMvc.perform(multipart("/api/v1/telemetry/import"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void importTelemetry_shouldReturnBadRequestWhenInvalidFileExtension() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "sample.txt", MediaType.TEXT_PLAIN_VALUE, "Sample content".getBytes());

        mockMvc.perform(multipart("/api/v1/telemetry/import")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void filterTelemetry_shouldReturnOk() throws Exception {
        when(filterService.validateFilters(any())).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/v1/telemetry/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[]"))
                .andExpect(status().isOk());
    }

    @Test
    void filterTelemetry_shouldReturnBadRequestWhenInvalidFilters() throws Exception {
        List<DataFilter> invalidFilters = Arrays.asList(
                new DataFilter("InvalidField", "Equals", "Value"),
                new DataFilter("AnotherInvalidField", "GreaterThan", 10));

        // Assuming your filter validation returns a list of invalid fields
        when(filterService.validateFilters(invalidFilters)).thenReturn(Arrays.asList("InvalidField", "AnotherInvalidField"));

        mockMvc.perform(post("/api/v1/telemetry/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilters)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid filters for fields: [InvalidField, AnotherInvalidField]")));
    }
}

