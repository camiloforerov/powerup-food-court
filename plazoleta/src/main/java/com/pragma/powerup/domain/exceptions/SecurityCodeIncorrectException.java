package com.pragma.powerup.domain.exceptions;

public class SecurityCodeIncorrectException extends RuntimeException {
    public SecurityCodeIncorrectException(String message) {
        super(message);
    }
}
