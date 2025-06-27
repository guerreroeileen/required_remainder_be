package com.example.required_remainder_be.exception;

import com.example.required_remainder_be.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle custom RequiredRemainderException
     */
    @ExceptionHandler(RequiredRemainderException.class)
    public ResponseEntity<ErrorResponse> handleRequiredRemainderException(
            RequiredRemainderException ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.error("RequiredRemainderException: {} | TraceId: {}", ex.getMessage(), traceId, ex);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .traceId(traceId)
                .build();
        
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
    
    /**
     * Handle ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ValidationException ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.warn("ValidationException: {} | TraceId: {}", ex.getMessage(), traceId);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .field(ex.getField())
                .path(request.getDescription(false))
                .traceId(traceId)
                .build();
        
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
    
    /**
     * Handle validation errors from @Valid annotations
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.warn("Validation errors: {} | TraceId: {}", ex.getBindingResult().getErrorCount(), traceId);
        
        List<ErrorResponse.ValidationError> validationErrors = new ArrayList<>();
        
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(new ErrorResponse.ValidationError(
                    fieldError.getField(),
                    fieldError.getDefaultMessage(),
                    fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null
            ));
        }
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed for " + ex.getBindingResult().getErrorCount() + " field(s)")
                .path(request.getDescription(false))
                .traceId(traceId)
                .validationErrors(validationErrors)
                .build();
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Handle type conversion errors
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.warn("Type mismatch: {} | TraceId: {}", ex.getMessage(), traceId);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Invalid value for parameter '" + ex.getName() + "': " + ex.getValue())
                .field(ex.getName())
                .path(request.getDescription(false))
                .traceId(traceId)
                .build();
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Handle JSON parsing errors
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.warn("Message not readable: {} | TraceId: {}", ex.getMessage(), traceId);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .traceId(traceId)
                .build();
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.warn("IllegalArgumentException: {} | TraceId: {}", ex.getMessage(), traceId);
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getDescription(false))
                .traceId(traceId)
                .build();
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Handle other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, WebRequest request) {
        
        String traceId = generateTraceId();
        logger.error("Unexpected error: {} | TraceId: {}", ex.getMessage(), traceId, ex);
        
        List<String> stackTrace = null;
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("An unexpected error occurred. Please try again later.")
                .path(request.getDescription(false))
                .traceId(traceId)
                .stackTrace(stackTrace)
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String generateTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
} 