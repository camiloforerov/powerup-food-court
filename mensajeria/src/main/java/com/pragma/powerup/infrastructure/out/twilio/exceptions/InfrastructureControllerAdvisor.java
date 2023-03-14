package com.pragma.powerup.infrastructure.out.twilio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class InfrastructureControllerAdvisor {
    private static final String MESSAGE = "message";

    @ExceptionHandler(TwilioException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            TwilioException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ignoredNoDataFoundException.getMessage()));
    }
}
