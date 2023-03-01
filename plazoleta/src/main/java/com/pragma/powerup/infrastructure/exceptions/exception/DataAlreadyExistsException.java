package com.pragma.powerup.infrastructure.exceptions.exception;

public class DataAlreadyExistsException extends RuntimeException {
    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
