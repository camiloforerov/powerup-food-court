package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.PositiveOrZero;

@Data
public class UpdateDishRequestDto {
    private Long dishId;
    @PositiveOrZero
    private Double price;
    private String description;
}
