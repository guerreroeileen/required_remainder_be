package com.example.required_remainder_be.service;

import com.example.required_remainder_be.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RequiredRemainderService Tests")
class RequiredRemainderServiceTest {

    @InjectMocks
    private RequiredRemainderService requiredRemainderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Problem Test Case 1: x=7, y=5, n=12345")
    void findMaxK_ProblemTestCase1_ShouldReturnCorrectResult() {
        long x = 7;
        long y = 5;
        long n = 12345;

        long result = requiredRemainderService.findMaxK(x, y, n);

        assertEquals(12339, result);
        assertEquals(5, result % x);
        assertTrue(result <= n);
    }

    @Test
    @DisplayName("Problem Test Case 5: x=499999993, y=9, n=1000000000")
    void findMaxK_ProblemTestCase5_ShouldReturnCorrectResult() {
        long x = 499999993;
        long y = 9;
        long n = 1000000000;
        long result = requiredRemainderService.findMaxK(x, y, n);
        assertEquals(999999995, result);
        assertEquals(9, result % x);
        assertTrue(result <= n);
    }

    @Test
    @DisplayName("Edge Case: n equals y")
    void findMaxK_WhenNEqualsY_ShouldReturnY() {
        long x = 7;
        long y = 3;
        long n = 3;

        long result = requiredRemainderService.findMaxK(x, y, n);

        assertEquals(3, result);
        assertEquals(3, result % x);
        assertTrue(result <= n);
    }

    @Test
    @DisplayName("Edge Case: maximum values")
    void findMaxK_WithMaximumValues_ShouldReturnCorrectResult() {
        long x = 1000000000;
        long y = 999999999;
        long n = 1000000000;

        // When
        long result = requiredRemainderService.findMaxK(x, y, n);

        // Then
        assertEquals(999999999, result);
        assertEquals(999999999, result % x);
        assertTrue(result <= n);
    }

    @Test
    @DisplayName("Validation: x less than 2")
    void findMaxK_WhenXLessThan2_ShouldThrowValidationException() {
        long x = 1;
        long y = 0;
        long n = 10;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            requiredRemainderService.findMaxK(x, y, n);
        });

        assertEquals("x must be between 2 and 10^9", exception.getMessage());
        assertEquals("x", exception.getField());
    }

    @Test
    @DisplayName("Validation: x greater than 10^9")
    void findMaxK_WhenXGreaterThan10To9_ShouldThrowValidationException() {
        long x = 1000000001;
        long y = 0;
        long n = 10;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            requiredRemainderService.findMaxK(x, y, n);
        });

        assertEquals("x must be between 2 and 10^9", exception.getMessage());
        assertEquals("x", exception.getField());
    }

    @Test
    @DisplayName("Validation: y negative")
    void findMaxK_WhenYNegative_ShouldThrowValidationException() {
        long x = 5;
        long y = -1;
        long n = 10;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            requiredRemainderService.findMaxK(x, y, n);
        });

        assertEquals("y must be between 0 and x-1", exception.getMessage());
        assertEquals("y", exception.getField());
    }

    @Test
    @DisplayName("Validation: n less than y")
    void findMaxK_WhenNLessThanY_ShouldThrowValidationException() {
        // Given
        long x = 5;
        long y = 3;
        long n = 2; // n < y

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            requiredRemainderService.findMaxK(x, y, n);
        });

        assertEquals("n must be between y and 10^9", exception.getMessage());
        assertEquals("n", exception.getField());
    }

    @Test
    @DisplayName("Validation: n greater than 10^9")
    void findMaxK_WhenNGreaterThan10To9_ShouldThrowValidationException() {
        long x = 5;
        long y = 3;
        long n = 1000000001;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            requiredRemainderService.findMaxK(x, y, n);
        });

        assertEquals("n must be between y and 10^9", exception.getMessage());
        assertEquals("n", exception.getField());
    }
}