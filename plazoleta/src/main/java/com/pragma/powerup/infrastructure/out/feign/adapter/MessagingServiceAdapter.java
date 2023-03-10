package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.spi.IMessagingServicePort;
import com.pragma.powerup.infrastructure.exceptions.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.feign.MessagingServiceFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.request.SendNotificationClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public class MessagingServiceAdapter implements IMessagingServicePort {
    private final MessagingServiceFeignClient messagingServiceFeignClient;
    @Override
    public boolean notifyClientOrderReady(String securityPin, String phoneNumber) {
        ResponseEntity<Boolean> response = this.messagingServiceFeignClient.sendMessage(new SendNotificationClientDto(phoneNumber, securityPin));
        try {
            return response.getBody();
        } catch (NullPointerException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
    }
}
