package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.INotificationServicePort;
import com.pragma.powerup.domain.spi.IMessageNotificationPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotificationUseCase implements INotificationServicePort {
    private final IMessageNotificationPort messageNotificationPort;

    /**
     * Notify client with the security code to the phone number
     *
     * @param notificationCode - security code
     * @param phoneNumber - phone to send the message to
     * @return if the message was sent
     * */
    @Override
    public boolean notifyClientSms(String notificationCode, String phoneNumber) {
        String message = Constants.NOTIFICATION_MESSAGE + notificationCode;
        return this.messageNotificationPort.sendNotificationToNumber(message, phoneNumber);
    }
}
