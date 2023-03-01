package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CreateRestaurantOwnerRequestDto;

public interface IAdminHandler {
    void createRestaurantOwner(CreateRestaurantOwnerRequestDto createRestaurantOwnerRequestDto);
}
