package com.logineko.telemetrymanagement.model.entity;

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

    @CsvBindByPosition(position = 2)
    private String gpsLongitude;

    @CsvBindByPosition(position = 3)
    private String gpsLatitude;

    @CsvBindByPosition(position = 4)
    private Double totalWorkingHoursCounter;

    @CsvBindByPosition(position = 5)
    private String groundSpeed;

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

    @CsvBindByPosition(position = 11)
    private String separationLosses;

    @CsvBindByPosition(position = 12)
    private String sieveLosses;

    @CsvBindByPosition(position = 13)
    private String chopper;

    @CsvCustomBindByPosition(position = 14, converter = DoubleConverter.class)
    private Double dieselTankLevel;

    @CsvCustomBindByPosition(position = 15, converter = IntegerConverter.class)
    private Integer numberOfPartialWidths;

    @CsvBindByPosition(position = 16)
    private String frontAttachmentOnOff;

    @CsvCustomBindByPosition(position = 17, converter = IntegerConverter.class)
    private Integer maxNumberOfPartialWidths;

    @CsvCustomBindByPosition(position = 18, converter = IntegerConverter.class)
    private Integer feedRakeSpeed;

    @CsvBindByPosition(position = 19)
    private String workingPosition;

    @CsvBindByPosition(position = 20)
    private String grainTankUnloading;

    @CsvBindByPosition(position = 21)
    private String mainDriveStatus;

    @CsvCustomBindByPosition(position = 22, converter = IntegerConverter.class)
    private Integer concavePosition;

    @CsvCustomBindByPosition(position = 23, converter = IntegerConverter.class)
    private Integer upperSievePosition;

    @CsvCustomBindByPosition(position = 24, converter = IntegerConverter.class)
    private Integer lowerSievePosition;

    @CsvBindByPosition(position = 25)
    private String grainTank70;

    @CsvBindByPosition(position = 26)
    private String grainTank100;

    @CsvBindByPosition(position = 27)
    private String grainMoistureContent;

    @CsvBindByPosition(position = 28)
    private String throughput;

    @CsvBindByPosition(position = 29)
    private String radialSpreaderSpeed;

    @CsvCustomBindByPosition(position = 30, converter = IntegerConverter.class)
    private Integer grainInReturns;

    @CsvBindByPosition(position = 31)
    private String channelPosition;

    @CsvBindByPosition(position = 32)
    private String yieldMeasurement;

    @CsvBindByPosition(position = 33)
    private String returnsAugerMeasurement;

    @CsvBindByPosition(position = 34)
    private String moistureMeasurement;

    @CsvBindByPosition(position = 35)
    private String typeOfCrop;

    @CsvCustomBindByPosition(position = 36, converter = IntegerConverter.class)
    private Integer specificCropWeight;

    @CsvBindByPosition(position = 37)
    private String autoPilotStatus;

    @CsvBindByPosition(position = 38)
    private String cruisePilotStatus;

    @CsvBindByPosition(position = 39)
    private String rateOfWork;

    @CsvBindByPosition(position = 40)
    private String yield;

    @CsvBindByPosition(position = 41)
    private String quantimeterCalibrationFactor;

    @CsvCustomBindByPosition(position = 42, converter = IntegerConverter.class)
    private Integer separationSensitivity;

    @CsvCustomBindByPosition(position = 43, converter = IntegerConverter.class)
    private Integer sieveSensitivity;
}
