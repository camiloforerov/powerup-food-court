package com.pragma.powerup.domain.exceptions;

public class CategoryDoesNotExistException extends RuntimeException {
    public CategoryDoesNotExistException(String message) {
        super(message);
    }
}
