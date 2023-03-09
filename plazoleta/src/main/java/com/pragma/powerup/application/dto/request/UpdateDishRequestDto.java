package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
public class UpdateDishRequestDto {
    @NotNull(message = "Dish id is required")
    private Long dishId;
    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;
    @Size(min = 1, max = 255, message = "Description must be between 1 and 150 characters")
    private String description;
}
