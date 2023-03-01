package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data
public class CreatedDishResponseDto {
    private Long id;
    private String name;
    private double price;
    private String description;
    private String urlPicture;
    private String categoryName;
    private boolean active;
}
