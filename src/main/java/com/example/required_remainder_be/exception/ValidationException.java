package com.example.required_remainder_be.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception for validation errors
 */
public class ValidationException extends RuntimeException {
    
    private final HttpStatus status;
    private final String errorCode;
    private final String field;
    
    public ValidationException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = "VALIDATION_ERROR";
        this.field = null;
    }
    
    public ValidationException(String message, String field) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
        this.errorCode = "VALIDATION_ERROR";
        this.field = field;
    }
    
    public ValidationException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.field = null;
    }
    
    public ValidationException(String message, HttpStatus status, String errorCode, String field) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.field = field;
    }
    
    public HttpStatus getStatus() {
        return status;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getField() {
        return field;
    }
} 