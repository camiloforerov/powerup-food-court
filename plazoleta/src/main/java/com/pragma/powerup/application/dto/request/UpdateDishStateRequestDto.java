package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateDishStateRequestDto {
    @NotNull(message = "Dish id is required")
    private Long dishId;
    @NotNull(message = "new state is required")
    private Boolean newState;
}
