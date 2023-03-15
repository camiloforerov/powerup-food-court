package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.NewOrderDishRequestDto;
import com.pragma.powerup.application.dto.response.CategorizedDishResponseDto;
import com.pragma.powerup.application.dto.response.DishOrderResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;

import java.util.List;

public interface IClientHandler {
    List<RestaurantForClientResponseDto> listRestaurants(int page, int numberOfElements);
    List<CategorizedDishResponseDto> listRestaurantCategorizedDishes(Long restaurantId, int page, int elementsPerPage);
    List<DishOrderResponseDto> createNewOrderClient(NewOrderDishRequestDto newOrderDishRequestDto);
    void cancelOrder(Long orderId);
}
