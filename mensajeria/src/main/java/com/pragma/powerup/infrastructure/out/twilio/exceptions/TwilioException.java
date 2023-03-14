package com.pragma.powerup.infrastructure.out.twilio.exceptions;

public class TwilioException extends RuntimeException {
    public TwilioException(String message) {
        super(message);
    }
}
