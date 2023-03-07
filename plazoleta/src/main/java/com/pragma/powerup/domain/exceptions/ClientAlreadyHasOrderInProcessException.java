package com.pragma.powerup.domain.exceptions;

public class ClientAlreadyHasOrderInProcessException extends RuntimeException {
    public ClientAlreadyHasOrderInProcessException(String message) {
        super(message);
    }
}
