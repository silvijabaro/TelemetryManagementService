package com.logineko.telemetrymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CSVMappingException extends RuntimeException {

    public CSVMappingException(String message) {
        super(message);
    }

    public CSVMappingException(String message, Exception e) {
        super(message, e);
    }
}
