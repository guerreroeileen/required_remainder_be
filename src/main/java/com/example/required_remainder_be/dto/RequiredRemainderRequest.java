package com.example.required_remainder_be.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequiredRemainderRequest {
    
    @NotNull(message = "x cannot be null")
    @Min(value = 2, message = "x must be at least 2")
    @Max(value = 1000000000, message = "x cannot exceed 1,000,000,000")
    private Long x;
    
    @NotNull(message = "y cannot be null")
    @Min(value = 0, message = "y must be non-negative")
    private Long y;
    
    @NotNull(message = "n cannot be null")
    @Min(value = 0, message = "n must be non-negative")
    @Max(value = 1000000000, message = "n cannot exceed 1,000,000,000")
    private Long n;
} 