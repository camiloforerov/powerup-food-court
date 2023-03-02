package com.pragma.powerup.domain.exceptions;

public class OwnerAlreadyHasRestaurantException extends RuntimeException {
    public OwnerAlreadyHasRestaurantException(String message) {
        super(message);
    }
}
