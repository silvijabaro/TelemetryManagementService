package com.logineko.telemetrymanagement.model.entity;

import com.logineko.telemetrymanagement.mapper.util.BooleanConverter;
import com.logineko.telemetrymanagement.mapper.util.DoubleConverter;
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

    @CsvBindByPosition(position = 0)
    @CsvDate(value = "MMM dd, yyyy, h:mm:ss a")
    private Date dateTime;
    @CsvBindByPosition(position = 1)
    private String serialNumber;
    @CsvCustomBindByPosition(position = 2, converter = DoubleConverter.class)
    private Double GPSLongitude;
    @CsvCustomBindByPosition(position = 3, converter = DoubleConverter.class)
    private Double GPSLatitude;
    @CsvCustomBindByPosition(position = 4, converter = DoubleConverter.class)
    private Double totalWorkingHours;
    @CsvCustomBindByPosition(position = 5, converter = IntegerConverter.class)
    private Integer engineSpeed;
    @CsvCustomBindByPosition(position = 6, converter = IntegerConverter.class)
    private Integer engineLoad;
    @CsvCustomBindByPosition(position = 7, converter = DoubleConverter.class)
    private Double fuelConsumption;
    @CsvCustomBindByPosition(position = 8, converter = DoubleConverter.class)
    private Double groundSpeedGearbox;
    @CsvCustomBindByPosition(position = 9, converter = DoubleConverter.class)
    private Double groundSpeedRadar;
    @CsvCustomBindByPosition(position = 10, converter = IntegerConverter.class)
    private Integer coolantTemperature;
    @CsvCustomBindByPosition(position = 11, converter = IntegerConverter.class)
    private Integer speedFrontPTO;
    @CsvCustomBindByPosition(position = 12, converter = IntegerConverter.class)
    private Integer speedRearPTO;
    @CsvCustomBindByPosition(position = 13, converter = IntegerConverter.class)
    private Integer currentGearShift;
    @CsvCustomBindByPosition(position = 14, converter = DoubleConverter.class)
    private Double ambientTemperature;
    @CsvCustomBindByPosition(position = 15, converter = IntegerConverter.class)
    private Integer parkingBrakeStatus;
    @CsvCustomBindByPosition(position = 16, converter = IntegerConverter.class)
    private Integer transverseDifferentialLockStatus;
    @CsvCustomBindByPosition(position = 17, converter = BooleanConverter.class)
    private Boolean allWheelDriveStatusActive;
    @CsvCustomBindByPosition(position = 18, converter = BooleanConverter.class)
    private Boolean actualStatusOfCreeperActive;
}
