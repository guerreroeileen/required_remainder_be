package com.example.required_remainder_be.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequiredRemainderException extends RuntimeException {
    
    private final HttpStatus status;
    private final String errorCode;
    
    public RequiredRemainderException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }

} 