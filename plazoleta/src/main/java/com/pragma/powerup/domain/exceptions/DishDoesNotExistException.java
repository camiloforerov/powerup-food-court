package com.pragma.powerup.domain.exceptions;

public class DishDoesNotExistException extends RuntimeException{
    public DishDoesNotExistException(String message) {
        super(message);
    }
}
