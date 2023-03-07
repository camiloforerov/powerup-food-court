package com.pragma.powerup.domain.exceptions;

public class DishesCannotBeEmptyException extends RuntimeException {
    public DishesCannotBeEmptyException(String message) {
        super(message);
    }
}
