package com.pragma.powerup.infrastructure.out.feign.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendNotificationClientDto {
    private String phone;
    private String message;
}
