package com.pragma.powerup.domain.spi;

public interface IMessageNotificationPort {
    boolean sendNotificationToNumber(String message, String phoneNumber);
}
