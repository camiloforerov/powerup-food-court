package com.pragma.powerup.domain.factory;

import com.pragma.powerup.domain.model.RestaurantModel;

public class FactoryAdminUseCase {
    public static RestaurantModel getRestaurantModel() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setAddress("House#4");
        restaurantModel.setNit(1234L);
        restaurantModel.setName("Mac Donald's");
        restaurantModel.setOwnerEmail("owner@gmail.com");
        restaurantModel.setPhone("123434545");
        restaurantModel.setUrlLogo("asdf/img.png");
        return restaurantModel;
    }
}
