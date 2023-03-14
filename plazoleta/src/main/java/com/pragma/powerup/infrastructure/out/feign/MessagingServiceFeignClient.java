package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.request.SendNotificationClientDto;
import com.pragma.powerup.infrastructure.out.feign.exceptions.MessagingServiceErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "messaging-service",
        url = "http://localhost:8083/messaging/v1",
        configuration = MessagingServiceErrorDecoder.class,
        decode404 = true
)
public interface MessagingServiceFeignClient {
    @PostMapping(value = "/employee/send-notification")
    boolean sendMessage(@RequestBody SendNotificationClientDto sendNotificationClientDto);

}
