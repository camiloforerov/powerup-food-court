package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;

public interface IOwnerServicePort {
    DishModel createDish(DishModel dishModel, RestaurantModel restaurantModel, Long categoryName);

    DishModel updateDish(Long dishId, Double price, String description);

    RestaurantModel getRestaurantByOwnerEmail(String ownerEmail);

    RestaurantEmployeeModel createEmployee(UserModel userModel, Long roleId, Long restaurantId);
}