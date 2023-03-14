package com.pragma.powerup.infrastructure.exceptions.exceptionhandler;

import com.pragma.powerup.infrastructure.exceptions.exception.AuthenticationException;
import com.pragma.powerup.infrastructure.exceptions.exception.BadRequestException;
import com.pragma.powerup.infrastructure.exceptions.exception.DataAlreadyExistsException;
import com.pragma.powerup.infrastructure.exceptions.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ignoredNoDataFoundException.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthErrorException(
            AuthenticationException ignoredAuthErrorException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MESSAGE, ignoredAuthErrorException.getMessage()));
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleDataAlreadyExistsException(
            DataAlreadyExistsException ignoredAuthErrorException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(MESSAGE, ignoredAuthErrorException.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(
            BadRequestException ignoredAuthErrorException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MESSAGE, ignoredAuthErrorException.getMessage()));
    }
    
}