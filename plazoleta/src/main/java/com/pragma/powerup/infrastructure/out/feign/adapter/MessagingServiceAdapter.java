package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.IMessagingServicePort;
import com.pragma.powerup.infrastructure.out.feign.MessagingServiceFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.request.SendNotificationClientDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessagingServiceAdapter implements IMessagingServicePort {
    private final MessagingServiceFeignClient messagingServiceFeignClient;
    @Override
    public boolean notifyClientOrderReady(String securityPin, String phoneNumber) {
        return this.messagingServiceFeignClient.sendMessage(new SendNotificationClientDto(phoneNumber, securityPin));
    }
}
