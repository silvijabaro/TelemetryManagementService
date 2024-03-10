package com.logineko.telemetrymanagement.repository;

import com.logineko.telemetrymanagement.entity.TractorTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TractorTelemetryRepository extends JpaRepository<TractorTelemetry, Long> {

}
