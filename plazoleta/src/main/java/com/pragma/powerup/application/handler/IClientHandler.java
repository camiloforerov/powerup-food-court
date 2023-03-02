package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;

import java.util.List;

public interface IClientHandler {
    List<RestaurantForClientResponseDto> listRestaurants(int page, int numberOfElements);
}
