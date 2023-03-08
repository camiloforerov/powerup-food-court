package com.pragma.powerup.application.dto.response;

import lombok.Data;

@Data
public class DishResponseDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String urlPicture;
    private boolean active;
    private int amount;
}
