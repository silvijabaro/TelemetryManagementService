package com.logineko.telemetrymanagement.model.dto;

import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FilteredTelemetry {
    private List<TractorTelemetry> tractors;
    private List<CombineTelemetry> combines;

    public FilteredTelemetry() {
        this.tractors = new ArrayList<>();
        this.combines = new ArrayList<>();
    }

}
