package com.logineko.telemetrymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnsupportedVehicleException extends RuntimeException {
    public UnsupportedVehicleException(String message) {
        super(message);
    }

    public UnsupportedVehicleException(String message, Exception e) {
        super(message, e);
    }
}