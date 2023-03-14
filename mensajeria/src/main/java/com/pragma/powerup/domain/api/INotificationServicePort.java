package com.pragma.powerup.domain.api;

public interface INotificationServicePort {
    boolean notifyClientSms(String notificationCode, String phoneNumber);
}
