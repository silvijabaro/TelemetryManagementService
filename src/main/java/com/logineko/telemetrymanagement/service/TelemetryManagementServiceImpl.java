package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.exception.CSVMappingException;
import com.logineko.telemetrymanagement.exception.UnsupportedVehicleException;
import com.logineko.telemetrymanagement.mapper.CSVMapper;
import com.logineko.telemetrymanagement.model.dto.DataFilter;
import com.logineko.telemetrymanagement.model.dto.FilteredTelemetry;
import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.repository.CombineTelemetryRepository;
import com.logineko.telemetrymanagement.repository.TractorTelemetryRepository;
import com.logineko.telemetrymanagement.util.Utils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TelemetryManagementServiceImpl implements TelemetryManagementService {

    public static final String TRACTOR_TELEMETRY = "LD_A";
    public static final String COMBINE_TELEMETRY = "LD_C";

    List<String> lowercaseTractorFields = Utils.getLowerCaseFieldNames(TractorTelemetry.class);
    List<String> lowercaseCombineFields = Utils.getLowerCaseFieldNames(CombineTelemetry.class);
    private final TractorTelemetryRepository tractorTelemetryRepository;

    private final CombineTelemetryRepository combineTelemetryRepository;

    private final CSVMapper csvMapper;


    @Override
    public void importTelemetry(MultipartFile file, String fileName) {
        if (StringUtils.startsWith(fileName, TRACTOR_TELEMETRY)) {
            importTractorTelemetry(file);
        } else if (StringUtils.startsWith(fileName, COMBINE_TELEMETRY)) {
            importCombineTelemetry(file);
        } else {
            throw new UnsupportedVehicleException("Vehicle not supported");
        }
    }

    private void importCombineTelemetry(MultipartFile file) {
        List<CombineTelemetry> list;
        try {
            list = csvMapper.CSVToCombine(file);
        } catch (Exception e) {
            log.error("Failed to import telemetry data for Combine. Exception:", e);
            throw new CSVMappingException("Was not able to import telemetry data for Combine");
        }
        combineTelemetryRepository.saveAll(list);
    }

    private void importTractorTelemetry(MultipartFile file) {
        List<TractorTelemetry> list;
        try {
            list = csvMapper.CSVToTractor(file);
        } catch (Exception e) {
            log.error("Failed to import telemetry data for Tractor. Exception:", e);
            throw new CSVMappingException("Was not able to import telemetry data for Tractor");
        }
        tractorTelemetryRepository.saveAll(list);
    }

    @Override
    public FilteredTelemetry filterTelemetry(List<DataFilter> dataFilter) {

        FilteredTelemetry filteredTelemetry = new FilteredTelemetry();

        for (DataFilter filter : dataFilter) {
            Specification<TractorTelemetry> tractorSpecification = createSpecification(filter, TractorTelemetry.class);
            Specification<CombineTelemetry> combineSpecification = createSpecification(filter, CombineTelemetry.class);

            List<TractorTelemetry> filteredTractorTelemetry = new ArrayList<>();
            String lowercaseFilterField = filter.getField().toLowerCase();
            if (lowercaseTractorFields.contains(lowercaseFilterField)) {
                filteredTractorTelemetry = tractorTelemetryRepository.findAll(tractorSpecification);
            }
            List<CombineTelemetry> filteredCombineTelemetry = new ArrayList<>();
            if (lowercaseCombineFields.contains(lowercaseFilterField)) {
                filteredCombineTelemetry = combineTelemetryRepository.findAll(combineSpecification);
            }

            filteredTelemetry.getTractors().addAll(filteredTractorTelemetry);
            filteredTelemetry.getCombines().addAll(filteredCombineTelemetry);
        }

        return filteredTelemetry;
    }

    private <T> Specification<T> createSpecification(DataFilter filter, Class<T> entityClass) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            String lowercaseField = StringUtils.uncapitalize(filter.getField());

            switch (filter.getOperation()) {
                case "Equals":
                    predicate = builder.and(predicate, builder.equal(root.get(lowercaseField), filter.getValue()));
                    break;
                case "LessThan":
                    predicate = builder.and(predicate, builder.lessThan(root.get(lowercaseField), (Comparable) filter.getValue()));
                    break;
                case "GreaterThan":
                    predicate = builder.and(predicate, builder.greaterThan(root.get(lowercaseField), (Comparable) filter.getValue()));
                    break;
                case "Contains":
                    predicate = builder.and(predicate, builder.like(root.get(lowercaseField), "%" + filter.getValue() + "%"));
                    break;
            }

            return predicate;
        };
    }

}
