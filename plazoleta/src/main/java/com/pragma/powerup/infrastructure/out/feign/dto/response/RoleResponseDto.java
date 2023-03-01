package com.pragma.powerup.infrastructure.out.feign.dto.response;

import lombok.Data;

@Data
public class RoleResponseDto {
    private Long id;
    private String name;
    private String description;
}
