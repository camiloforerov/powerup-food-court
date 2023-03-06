package com.pragma.powerup.domain.factory;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;

public class FactoryClientUseCase {
    public static RestaurantModel getRestaurantModel() {
        RestaurantModel restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setOwnerEmail("owner@gmail.com");
        restaurantModel.setNit(11010101L);
        restaurantModel.setName("Giorno's pizza");
        restaurantModel.setPhone("12323243");
        restaurantModel.setAddress("Home#4");
        restaurantModel.setUrlLogo("asdfsdf.com/img.png");
        return restaurantModel;
    }

    public static DishModel getDishModel(CategoryModel categoryModel, RestaurantModel restaurantModel) {
        DishModel dishModelBase = new DishModel();
        dishModelBase.setName("Pizza");
        dishModelBase.setCategory(categoryModel);
        dishModelBase.setPrice(60000);
        dishModelBase.setRestaurant(restaurantModel);
        return dishModelBase;
    }

    public static CategoryModel getCategoryModel() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setName("Italian");
        categoryModel.setDescription("Italian food");
        return categoryModel;
    }
}
