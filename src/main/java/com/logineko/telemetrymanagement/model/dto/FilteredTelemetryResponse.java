package com.logineko.telemetrymanagement.model.dto;

import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilteredTelemetryResponse {
    private List<TractorTelemetry> tractors;
    private List<CombineTelemetry> combines;

    public FilteredTelemetryResponse() {
        this.tractors = new ArrayList<>();
        this.combines = new ArrayList<>();
    }

}
