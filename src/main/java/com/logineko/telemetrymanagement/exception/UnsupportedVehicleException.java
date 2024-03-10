package com.logineko.telemetrymanagement.exception;

public class UnsupportedVehicleException extends RuntimeException {
    public UnsupportedVehicleException(String message) {
        super(message);
    }

    public UnsupportedVehicleException(String message, Exception e) {
        super(message, e);
    }
}