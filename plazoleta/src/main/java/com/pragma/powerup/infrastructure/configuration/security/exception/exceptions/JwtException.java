package com.pragma.powerup.infrastructure.configuration.security.exception.exceptions;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
