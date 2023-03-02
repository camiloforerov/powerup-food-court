package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.RestaurantModel;

import java.util.List;

public interface IClientServicePort {
    List<RestaurantModel> listRestaurants(int pageNumber, int numbersOfElements);
}
