package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data
public class CreateEmployeeResponseDto {
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String email;
}
