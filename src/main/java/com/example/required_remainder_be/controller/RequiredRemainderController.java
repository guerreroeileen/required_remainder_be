package com.example.required_remainder_be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("required-remainder")
public class RequiredRemainderController {

    @GetMapping
    public ResponseEntity<String> getRequiredRemainder() {
        return ResponseEntity.ok("Required Remainder");
    }
}
