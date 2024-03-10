package com.logineko.telemetrymanagement.mapper;

import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class CSVMapper {
    public List<TractorTelemetry> CSVToTractor(MultipartFile file) throws Exception {
        Reader reader = new InputStreamReader(file.getInputStream()) ;

        return new CsvToBeanBuilder<TractorTelemetry>(reader)
                .withType(TractorTelemetry.class)
                .withSkipLines(1)
                .withSeparator(';')
                .build()
                .parse();
    }
}
