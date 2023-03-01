package com.pragma.powerup.domain.exceptions;

public class NoRestaurantForOwnerFoundException extends RuntimeException {
    public NoRestaurantForOwnerFoundException(String message) {
        super(message);
    }
}
