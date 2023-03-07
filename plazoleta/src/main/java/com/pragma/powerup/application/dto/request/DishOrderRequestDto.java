package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class DishOrderRequestDto {

    @NotNull(message = "Dish id is required")
    @Valid
    private Long dishId;

    @NotNull(message = "Amount value is required")
    @Positive(message = "Only positive values allowed")
    @Valid
    private int amount;
}
