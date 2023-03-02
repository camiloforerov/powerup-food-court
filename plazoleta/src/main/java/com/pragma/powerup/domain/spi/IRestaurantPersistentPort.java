package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RestaurantModel;

import java.util.List;
import java.util.Optional;

public interface IRestaurantPersistentPort {
    void saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantByOwnerEmail(String ownerEmail);
    Optional<RestaurantModel> getRestaurantById(Long restaurantId);
    List<RestaurantModel> listByPageAndElements(int pageNumber, int numbersOfElements);
}
