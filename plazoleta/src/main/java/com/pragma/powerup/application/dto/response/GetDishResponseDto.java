package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data
public class GetDishResponseDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String urlPicture;
}
