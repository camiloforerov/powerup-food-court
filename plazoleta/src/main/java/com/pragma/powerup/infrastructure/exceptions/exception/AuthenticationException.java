package com.pragma.powerup.infrastructure.exceptions.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
