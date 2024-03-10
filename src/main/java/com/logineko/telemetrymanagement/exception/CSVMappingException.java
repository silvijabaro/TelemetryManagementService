package com.logineko.telemetrymanagement.exception;

public class CSVMappingException extends RuntimeException {

    public CSVMappingException(String message) {
        super(message);
    }

    public CSVMappingException(String message, Exception e) {
        super(message, e);
    }
}
