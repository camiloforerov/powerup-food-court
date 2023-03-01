package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CreateRestaurantRequestDto;

public interface IAdminHandler {
    void createRestaurant(CreateRestaurantRequestDto createRestaurantRequestDto);
}
