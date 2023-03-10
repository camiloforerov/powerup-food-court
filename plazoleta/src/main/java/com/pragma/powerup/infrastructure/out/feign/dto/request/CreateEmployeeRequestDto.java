package com.pragma.powerup.infrastructure.out.feign.dto.request;

import lombok.Data;

@Data
public class CreateEmployeeRequestDto {
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private String password;
    private Long roleId;
}
