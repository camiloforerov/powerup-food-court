package com.pragma.powerup.domain.spi;

public interface IMessagingServicePort {
    boolean notifyClient(String message, String phoneNumber);
}
