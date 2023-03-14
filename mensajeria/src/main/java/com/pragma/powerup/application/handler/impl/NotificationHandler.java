package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.SendNotificationClientRequestDto;
import com.pragma.powerup.application.handler.INotificationHandler;
import com.pragma.powerup.domain.api.INotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationHandler implements INotificationHandler {
    private final INotificationServicePort notificationServicePort;

    /**
     * Send notification to client with its phone number and a verification code
     *
     * @param sendNotificationClientRequestDto - phone number and message to be send
     * @return if the message was sent
     * */
    @Override
    public boolean notifyClient(SendNotificationClientRequestDto sendNotificationClientRequestDto) {
        return this.notificationServicePort.notifyClientSms(
                sendNotificationClientRequestDto.getSecurityCode(),
                sendNotificationClientRequestDto.getPhone()
        );
    }
}
