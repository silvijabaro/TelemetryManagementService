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
public class CombineTelemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CsvBindByPosition(position = 0)
    @CsvDate(value = "MMM dd, yyyy, h:mm:ss a")
    private Date dateTime;

    @CsvBindByPosition(position = 1)
    private String serialNumber;

    @CsvCustomBindByPosition(position = 2, converter = DoubleConverter.class)
    private Double gpsLongitude;

    @CsvCustomBindByPosition(position = 3, converter = DoubleConverter.class)
    private Double gpsLatitude;

    @CsvCustomBindByPosition(position = 4, converter = DoubleConverter.class)
    private Double totalWorkingHoursCounter;

    @CsvCustomBindByPosition(position = 5, converter = DoubleConverter.class)
    private Double groundSpeed;

    @CsvCustomBindByPosition(position = 6, converter = IntegerConverter.class)
    private Integer engineSpeed;

    @CsvCustomBindByPosition(position = 7, converter = IntegerConverter.class)
    private Integer engineLoad;

    @CsvCustomBindByPosition(position = 8, converter = IntegerConverter.class)
    private Integer drumSpeed;

    @CsvCustomBindByPosition(position = 9, converter = IntegerConverter.class)
    private Integer fanSpeed;

    @CsvCustomBindByPosition(position = 10, converter = IntegerConverter.class)
    private Integer rotorStrawWalkerSpeed;

    @CsvCustomBindByPosition(position = 11, converter = DoubleConverter.class)
    private Double separationLosses;

    @CsvCustomBindByPosition(position = 12, converter = DoubleConverter.class)
    private Double sieveLosses;

    @CsvCustomBindByPosition(position = 13, converter = BooleanConverter.class)
    private Boolean chopper;

    @CsvCustomBindByPosition(position = 14, converter = DoubleConverter.class)
    private Double dieselTankLevel;

    @CsvCustomBindByPosition(position = 15, converter = IntegerConverter.class)
    private Integer numberOfPartialWidths;

    @CsvCustomBindByPosition(position = 16, converter = BooleanConverter.class)
    private Boolean frontAttachmentOnOff;

    @CsvCustomBindByPosition(position = 17, converter = IntegerConverter.class)
    private Integer maxNumberOfPartialWidths;

    @CsvCustomBindByPosition(position = 18, converter = IntegerConverter.class)
    private Integer feedRakeSpeed;

    @CsvCustomBindByPosition(position = 19, converter = BooleanConverter.class)
    private Boolean workingPosition;

    @CsvCustomBindByPosition(position = 20, converter = BooleanConverter.class)
    private Boolean grainTankUnloading;

    @CsvCustomBindByPosition(position = 21, converter = BooleanConverter.class)
    private Boolean mainDriveStatus;

    @CsvCustomBindByPosition(position = 22, converter = IntegerConverter.class)
    private Integer concavePosition;

    @CsvCustomBindByPosition(position = 23, converter = IntegerConverter.class)
    private Integer upperSievePosition;

    @CsvCustomBindByPosition(position = 24, converter = IntegerConverter.class)
    private Integer lowerSievePosition;

    @CsvCustomBindByPosition(position = 25, converter = BooleanConverter.class)
    private Boolean grainTank70;

    @CsvCustomBindByPosition(position = 26, converter = BooleanConverter.class)
    private Boolean grainTank100;

    @CsvCustomBindByPosition(position = 27, converter = DoubleConverter.class)
    private Double grainMoistureContent;

    @CsvCustomBindByPosition(position = 28, converter = DoubleConverter.class)
    private Double throughput;

    @CsvCustomBindByPosition(position = 29, converter = IntegerConverter.class)
    private Integer radialSpreaderSpeed;

    @CsvCustomBindByPosition(position = 30, converter = IntegerConverter.class)
    private Integer grainInReturns;

    @CsvCustomBindByPosition(position = 31, converter = DoubleConverter.class)
    private Double channelPosition;

    @CsvCustomBindByPosition(position = 32, converter = BooleanConverter.class)
    private Boolean yieldMeasurement;

    @CsvCustomBindByPosition(position = 33, converter = DoubleConverter.class)
    private Double returnsAugerMeasurement;

    @CsvCustomBindByPosition(position = 34, converter = BooleanConverter.class)
    private Boolean moistureMeasurement;

    @CsvBindByPosition(position = 35)
    private String typeOfCrop;

    @CsvCustomBindByPosition(position = 36, converter = IntegerConverter.class)
    private Integer specificCropWeight;

    @CsvCustomBindByPosition(position = 37, converter = BooleanConverter.class)
    private Boolean autoPilotStatus;

    @CsvCustomBindByPosition(position = 38, converter = DoubleConverter.class)
    private Double cruisePilotStatus;

    @CsvCustomBindByPosition(position = 39, converter = DoubleConverter.class)
    private Double rateOfWork;

    @CsvCustomBindByPosition(position = 40, converter = DoubleConverter.class)
    private Double yield;

    @CsvCustomBindByPosition(position = 41, converter = DoubleConverter.class)
    private Double quantimeterCalibrationFactor;

    @CsvCustomBindByPosition(position = 42, converter = IntegerConverter.class)
    private Integer separationSensitivity;

    @CsvCustomBindByPosition(position = 43, converter = IntegerConverter.class)
    private Integer sieveSensitivity;
}
