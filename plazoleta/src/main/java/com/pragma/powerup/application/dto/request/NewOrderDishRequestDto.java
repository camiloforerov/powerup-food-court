package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class NewOrderDishRequestDto {
    @NotNull(message = "Restaurant id is required")
    @Valid
    Long restaurantId;

    @NotNull(message = "Dishes are required")
    @Valid
    List<DishOrderRequestDto> dishes;
}
