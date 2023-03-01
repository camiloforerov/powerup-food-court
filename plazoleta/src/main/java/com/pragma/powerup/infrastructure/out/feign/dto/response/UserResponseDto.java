package com.pragma.powerup.infrastructure.out.feign.dto.response;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private String role;
}
