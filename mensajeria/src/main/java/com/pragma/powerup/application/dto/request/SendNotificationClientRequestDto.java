package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SendNotificationClientRequestDto {
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Pattern not allowed")
    @Size(min = 7,
            max = 13,
            message = "Phone number must be between 7 and 13 characters")
    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
    @NotBlank(message = "Message cannot be blank")
    @Size(min = 3, max = 255)
    private String message;
}
