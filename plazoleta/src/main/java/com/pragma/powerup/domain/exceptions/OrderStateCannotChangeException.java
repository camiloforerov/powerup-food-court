package com.pragma.powerup.domain.exceptions;

public class OrderStateCannotChangeException extends RuntimeException {
    public OrderStateCannotChangeException(String message) {
        super(message);
    }
}
