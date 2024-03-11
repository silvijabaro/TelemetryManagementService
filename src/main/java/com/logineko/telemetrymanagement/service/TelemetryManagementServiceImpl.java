package com.logineko.telemetrymanagement.service;

import com.logineko.telemetrymanagement.exception.CSVMappingException;
import com.logineko.telemetrymanagement.exception.UnsupportedVehicleException;
import com.logineko.telemetrymanagement.mapper.CSVMapper;
import com.logineko.telemetrymanagement.model.dto.DataFilter;
import com.logineko.telemetrymanagement.model.dto.FilteredTelemetryResponse;
import com.logineko.telemetrymanagement.model.entity.CombineTelemetry;
import com.logineko.telemetrymanagement.model.entity.TractorTelemetry;
import com.logineko.telemetrymanagement.repository.CombineTelemetryRepository;
import com.logineko.telemetrymanagement.repository.TractorTelemetryRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.logineko.telemetrymanagement.filter.ValidFilters.combineFieldsLowerCase;
import static com.logineko.telemetrymanagement.filter.ValidFilters.tractorFieldsLowerCase;

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
    public FilteredTelemetryResponse filterTelemetry(List<DataFilter> dataFilter) {
        // Create a new instance of FilteredTelemetry to store the filtered results
        FilteredTelemetryResponse filteredTelemetryResponse = new FilteredTelemetryResponse();

        // Lists to store specifications for tractors and combines separately
        List<Specification<TractorTelemetry>> tractorSpecifications = new ArrayList<>();
        List<Specification<CombineTelemetry>> combineSpecifications = new ArrayList<>();

        for (DataFilter filter : dataFilter) {
            // Create specifications for tractors and combines based on the filter
            Specification<TractorTelemetry> tractorSpecification = createSpecification(filter, TractorTelemetry.class);
            Specification<CombineTelemetry> combineSpecification = createSpecification(filter, CombineTelemetry.class);

            // Check if the filter field is applicable to tractors/combines and add the specification to the list
            String lowercaseFilterField = filter.getField().toLowerCase();
            if (tractorFieldsLowerCase.contains(lowercaseFilterField)) {
                tractorSpecifications.add(tractorSpecification);
            }
            if (combineFieldsLowerCase.contains(lowercaseFilterField)) {
                combineSpecifications.add(combineSpecification);
            }
        }

        // Combine tractor/combine specifications with 'and' logic
        Specification<TractorTelemetry> combinedTractorSpecification = tractorSpecifications.stream()
                .reduce(Specification::and)
                .orElse(null);

        Specification<CombineTelemetry> combinedCombineSpecification = combineSpecifications.stream()
                .reduce(Specification::and)
                .orElse(null);

        // Retrieve filtered results for tractors/combines based on combined specifications
        List<TractorTelemetry> filteredTractorTelemetry = combinedTractorSpecification != null ?
                tractorTelemetryRepository.findAll(combinedTractorSpecification) :
                Collections.emptyList();

        List<CombineTelemetry> filteredCombineTelemetry = combinedCombineSpecification != null ?
                combineTelemetryRepository.findAll(combinedCombineSpecification) :
                Collections.emptyList();

        filteredTelemetryResponse.getTractors().addAll(filteredTractorTelemetry);
        filteredTelemetryResponse.getCombines().addAll(filteredCombineTelemetry);

        return filteredTelemetryResponse;
    }

    private <T> Specification<T> createSpecification(DataFilter filter, Class<T> entityClass) {
        return (root, query, builder) -> {
            // Initialize a predicate with conjunction (logical AND)
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
