package com.pragma.powerup.domain.exceptions;

public class OrderIsAlreadyAssignedToChefException extends RuntimeException {
    public OrderIsAlreadyAssignedToChefException(String message) {
        super(message);
    }
}
