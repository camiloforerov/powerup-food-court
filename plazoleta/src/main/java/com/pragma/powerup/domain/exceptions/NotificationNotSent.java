package com.pragma.powerup.domain.exceptions;

public class NotificationNotSent extends RuntimeException {
    public NotificationNotSent(String message) {
        super(message);
    }
}
