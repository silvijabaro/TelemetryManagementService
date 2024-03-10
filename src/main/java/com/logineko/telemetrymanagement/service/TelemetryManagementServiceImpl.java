package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.exception.CSVMappingException;
import com.logineko.telemetrymanagement.exception.UnsupportedVehicleException;
import com.logineko.telemetrymanagement.mapper.CSVMapper;
import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.repository.CombineTelemetryRepository;
import com.logineko.telemetrymanagement.repository.TractorTelemetryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TelemetryManagementServiceImpl implements TelemetryManagementService {

    public static final String TRACTOR_TELEMETRY = "LD_A";
    public static final String COMBINE_TELEMETRY = "LD_C";
    private final TractorTelemetryRepository tractorTelemetryRepository;

    private final CombineTelemetryRepository combineTelemetryRepository;

    private final CSVMapper csvMapper;


    @Override
    public void importTelemetry(MultipartFile file, String fileName) {
        if (StringUtils.startsWith(fileName, TRACTOR_TELEMETRY)) {
            List<TractorTelemetry> list;
            try {
                list = csvMapper.CSVToTractor(file);
            } catch (Exception e) {
                log.error("Failed to import telemetry data for Tractor. Exception:", e);
                throw new CSVMappingException("Was not able to import telemetry data for Tractor");
            }
            tractorTelemetryRepository.saveAll(list);
        } else if (StringUtils.startsWith(fileName, COMBINE_TELEMETRY)) {
            List<CombineTelemetry> list;
            try {
                list = csvMapper.CSVToCombine(file);
            } catch (Exception e) {
                log.error("Failed to import telemetry data for Tractor. Exception:", e);
                throw new CSVMappingException("Was not able to import telemetry data for Combine");
            }
            combineTelemetryRepository.saveAll(list);
        } else {
            throw new UnsupportedVehicleException("Vehicle not supported");
        }
    }

    @Override
    public List<TractorTelemetry> filterTelemetry(String fieldName, Object value, String operator) {
        throw new UnsupportedOperationException();
    }

}
