package com.pragma.powerup.domain.exceptions;

public class EmployeeDoesNotBelongToAnyRestaurantException extends RuntimeException {
    public EmployeeDoesNotBelongToAnyRestaurantException(String message) {
        super(message);
    }
}
