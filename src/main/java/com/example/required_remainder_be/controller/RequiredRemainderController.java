package com.example.required_remainder_be.controller;

import com.example.required_remainder_be.dto.RequiredRemainderRequest;
import com.example.required_remainder_be.service.RequiredRemainderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.required_remainder_be.constants.GeneralConstants.API_REQUIRED_REMAINDER_PATH;
import static com.example.required_remainder_be.constants.GeneralConstants.SOLVE_PATH;

@RestController
@RequestMapping(API_REQUIRED_REMAINDER_PATH)
public class RequiredRemainderController {

    private final RequiredRemainderService requiredRemainderService;

    public RequiredRemainderController(RequiredRemainderService requiredRemainderService) {
        this.requiredRemainderService = requiredRemainderService;
    }

    @PostMapping(SOLVE_PATH)
    public ResponseEntity<List<Long>> solveCases(@Valid @RequestBody List<RequiredRemainderRequest> requests) {
        List<Long> results = requiredRemainderService.solveCases(requests);
        return ResponseEntity.ok(results);
    }
}
