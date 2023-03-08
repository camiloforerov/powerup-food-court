package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;

import java.util.Optional;

public interface IRestaurantEmployeePersistentPort {
    RestaurantEmployeeModel save(RestaurantEmployeeModel restaurantEmployeeModel);
    Optional<RestaurantEmployeeModel> findByEmployeeEmail(String email);
}
