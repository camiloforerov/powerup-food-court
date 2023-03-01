package com.pragma.powerup.infrastructure.out.jpa.exceptions.exception;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
}
