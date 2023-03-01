package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.RestaurantOwnerResponseDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;

public interface IUserHandler {
    RestaurantOwnerResponseDto getRestaurantOwner(String email);
    UserResponseDto getUserByEmail(String email);
}
