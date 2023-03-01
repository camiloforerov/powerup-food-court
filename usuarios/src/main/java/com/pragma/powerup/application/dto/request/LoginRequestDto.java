package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {
    @Valid
    @Email
    private String email;
    @Valid
    @NotBlank
    private String password;
}
