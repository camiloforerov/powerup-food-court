package com.pragma.powerup.domain.spi;

public interface IMessagingServicePort {
    boolean notifyClientOrderReady(String securityPin, String phoneNumber);
}
