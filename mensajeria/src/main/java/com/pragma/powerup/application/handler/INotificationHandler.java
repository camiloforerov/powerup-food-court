package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.SendNotificationClientRequestDto;

public interface INotificationHandler {
    boolean notifyClient(SendNotificationClientRequestDto sendNotificationClientRequestDto);
}
