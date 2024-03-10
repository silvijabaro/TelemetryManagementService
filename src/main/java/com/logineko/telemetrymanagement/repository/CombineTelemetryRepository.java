package com.logineko.telemetrymanagement.repository;

import com.logineko.telemetrymanagement.entity.CombineTelemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombineTelemetryRepository extends JpaRepository<CombineTelemetry, Long> {
}
