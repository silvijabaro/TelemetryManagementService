package com.logineko.telemetrymanagement.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TractorTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
