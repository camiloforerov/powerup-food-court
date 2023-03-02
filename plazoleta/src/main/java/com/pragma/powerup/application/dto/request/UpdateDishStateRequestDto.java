package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;

@Data
public class UpdateDishStateRequestDto {
    @Valid
    private Long dishId;
    @Valid
    private Boolean newState;
}
