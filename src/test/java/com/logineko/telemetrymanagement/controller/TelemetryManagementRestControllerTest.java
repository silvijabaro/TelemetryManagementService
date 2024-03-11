package com.logineko.telemetrymanagement.controller;

import com.logineko.telemetrymanagement.filter.FilterService;
import com.logineko.telemetrymanagement.service.TelemetryManagementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TelemetryManagementRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TelemetryManagementService telemetryManagementService;

    @MockBean
    private FilterService filterService;


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
}

