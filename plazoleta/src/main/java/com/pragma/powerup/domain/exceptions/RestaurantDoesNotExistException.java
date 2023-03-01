package com.pragma.powerup.domain.exceptions;

public class RestaurantDoesNotExistException extends RuntimeException {
    public RestaurantDoesNotExistException(String message) {
        super(message);
    }
}
