package com.example.required_remainder_be.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String errorCode;
    private String message;
    private String field;
    private String path;
    private String method;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    private String traceId;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> stackTrace;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ValidationError> validationErrors;
    
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
        private String rejectedValue;
    }
} 