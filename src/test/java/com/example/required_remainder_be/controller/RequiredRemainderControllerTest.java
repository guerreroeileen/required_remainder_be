package com.example.required_remainder_be.controller;

import com.example.required_remainder_be.dto.RequiredRemainderRequest;
import com.example.required_remainder_be.exception.RequiredRemainderException;
import com.example.required_remainder_be.exception.ValidationException;
import com.example.required_remainder_be.service.RequiredRemainderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RequiredRemainderController Unit Tests")
class RequiredRemainderControllerTest {

    @Mock
    private RequiredRemainderService requiredRemainderService;

    @InjectMocks
    private RequiredRemainderController requiredRemainderController;

    private List<RequiredRemainderRequest> validRequests;

    @BeforeEach
    void setUp() {
        RequiredRemainderRequest validRequest = new RequiredRemainderRequest();
        validRequest.setX(10L);
        validRequest.setY(3L);
        validRequest.setN(20L);

        validRequests = Arrays.asList(validRequest);
    }

    @Test
    @DisplayName("Should return success response with valid single request")
    void solveCases_WithValidSingleRequest_ShouldReturnSuccess() {
        List<Long> expectedResults = Arrays.asList(13L);
        when(requiredRemainderService.solveCases(anyList())).thenReturn(expectedResults);

        ResponseEntity<List<Long>> response = requiredRemainderController.solveCases(validRequests);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(13L, response.getBody().get(0));

        verify(requiredRemainderService, times(1)).solveCases(validRequests);
    }

    @Test
    @DisplayName("Should return success response with multiple valid requests")
    void solveCases_WithMultipleValidRequests_ShouldReturnSuccess() {
        RequiredRemainderRequest request1 = new RequiredRemainderRequest();
        request1.setX(5L);
        request1.setY(2L);
        request1.setN(15L);

        RequiredRemainderRequest request2 = new RequiredRemainderRequest();
        request2.setX(7L);
        request2.setY(1L);
        request2.setN(25L);

        List<RequiredRemainderRequest> requests = Arrays.asList(request1, request2);
        List<Long> expectedResults = Arrays.asList(12L, 22L);
        when(requiredRemainderService.solveCases(anyList())).thenReturn(expectedResults);
        ResponseEntity<List<Long>> response = requiredRemainderController.solveCases(requests);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(12L, response.getBody().get(0));
        assertEquals(22L, response.getBody().get(1));
        verify(requiredRemainderService, times(1)).solveCases(requests);
    }

    @Test
    @DisplayName("Should return success response with large numbers")
    void solveCases_WithLargeNumbers_ShouldReturnSuccess() {
        RequiredRemainderRequest largeRequest = new RequiredRemainderRequest();
        largeRequest.setX(1000000000L);
        largeRequest.setY(999999999L);
        largeRequest.setN(1000000000L);
        List<RequiredRemainderRequest> requests = Arrays.asList(largeRequest);
        List<Long> expectedResults = Arrays.asList(999999999L);
        when(requiredRemainderService.solveCases(anyList())).thenReturn(expectedResults);
        ResponseEntity<List<Long>> response = requiredRemainderController.solveCases(requests);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(requiredRemainderService, times(1)).solveCases(requests);
    }

    @Test
    @DisplayName("Should handle service throwing ValidationException")
    void solveCases_WhenServiceThrowsValidationException_ShouldPropagateException() {
        ValidationException validationException = new ValidationException("Invalid constraints", "x");
        when(requiredRemainderService.solveCases(anyList())).thenThrow(validationException);
        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            requiredRemainderController.solveCases(validRequests);
        });
        assertEquals("Invalid constraints", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        verify(requiredRemainderService, times(1)).solveCases(validRequests);
    }

    @Test
    @DisplayName("Should handle service throwing UnprocessableEntityException")
    void solveCases_WhenServiceThrowsUnprocessableEntityException_ShouldPropagateException() {
        RequiredRemainderException unprocessableException = new RequiredRemainderException(
            "No solution found", 
            HttpStatus.UNPROCESSABLE_ENTITY, 
            "Unprocessable Entity"
        );
        when(requiredRemainderService.solveCases(anyList())).thenThrow(unprocessableException);
        RequiredRemainderException thrown = assertThrows(RequiredRemainderException.class, () -> {
            requiredRemainderController.solveCases(validRequests);
        });
        assertEquals("No solution found", thrown.getMessage());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, thrown.getStatus());
        assertEquals("Unprocessable Entity", thrown.getErrorCode());
        verify(requiredRemainderService, times(1)).solveCases(validRequests);
    }
}