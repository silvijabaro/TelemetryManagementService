package com.logineko.telemetrymanagement.model.entity;

import com.logineko.telemetrymanagement.mapper.util.IntegerConverter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TractorTelemetry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CsvBindByPosition(position=0)
    @CsvDate(value = "MMM dd, yyyy, h:mm:ss a")
    private Date dateTime;
    @CsvBindByPosition(position = 1)
    private String serialNumber;
    @CsvBindByPosition(position = 2)
    private String GPSLongitude;
    @CsvBindByPosition(position = 3)
    private String GPSLatitude;
    @CsvBindByPosition(position = 4)
    private Double totalWorkingHours;
    @CsvCustomBindByPosition(position = 5, converter = IntegerConverter.class)
    private Integer engineSpeed;
    @CsvCustomBindByPosition(position = 6, converter = IntegerConverter.class)
    private Integer engineLoad;
    @CsvBindByPosition(position = 7)
    private String fuelConsumption;
    @CsvBindByPosition(position = 8)
    private String groundSpeedGearbox;
    @CsvBindByPosition(position = 9)
    private String groundSpeedRadar;
    @CsvCustomBindByPosition(position = 10, converter = IntegerConverter.class)
    private Integer coolantTemperature;
    @CsvCustomBindByPosition(position = 11, converter = IntegerConverter.class)
    private Integer speedFrontPTO;
    @CsvCustomBindByPosition(position = 12, converter = IntegerConverter.class)
    private Integer speedRearPTO;
    @CsvCustomBindByPosition(position = 13, converter = IntegerConverter.class)
    private Integer currentGearShift;
    @CsvBindByPosition(position = 14)
    private String ambientTemperature;
    @CsvCustomBindByPosition(position = 15, converter = IntegerConverter.class)
    private Integer parkingBrakeStatus;
    @CsvCustomBindByPosition(position = 16, converter = IntegerConverter.class)
    private Integer transverseDifferentialLockStatus;
    @CsvBindByPosition(position = 17)
    private String allWheelDriveStatusActive;
    @CsvBindByPosition(position = 18)
    private String actualStatusOfCreeperActive;
}
