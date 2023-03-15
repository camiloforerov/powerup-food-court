package com.pragma.powerup.domain.api;

public interface INotificationServicePort {
    boolean notifyClientSms(String message, String phoneNumber);
}
