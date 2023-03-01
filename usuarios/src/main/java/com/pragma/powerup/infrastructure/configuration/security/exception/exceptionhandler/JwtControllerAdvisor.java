package com.pragma.powerup.infrastructure.configuration.security.exception.exceptionhandler;

import com.pragma.powerup.infrastructure.configuration.security.exception.exceptions.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class JwtControllerAdvisor {
    private static final String MESSAGE = "message";

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleJwtTokenException(
            JwtException ignoredJwtTokenException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(MESSAGE, ignoredJwtTokenException.getMessage()));
    }
}
