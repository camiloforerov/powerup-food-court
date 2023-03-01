package com.pragma.powerup.domain.factory;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import org.apache.catalina.User;

public class FactoryOwnerUseCase {

    public static CategoryModel getCategoryModel() {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);
        categoryModel.setName("Italian");
        categoryModel.setDescription("Italian food");
        return categoryModel;
    }
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

    public static DishModel getBaseDishModel(CategoryModel categoryModel, RestaurantModel restaurantModel) {
        DishModel dishModelBase = new DishModel();
        dishModelBase.setName("Pizza");
        dishModelBase.setCategory(categoryModel);
        dishModelBase.setPrice(60000);
        dishModelBase.setRestaurant(restaurantModel);
        return dishModelBase;
    }

    public static DishModel getCreatedDishModel(CategoryModel categoryModel, RestaurantModel restaurantModel) {
        DishModel dishModel = new DishModel();
        dishModel.setId(1L);
        dishModel.setName("Pizza");
        dishModel.setCategory(categoryModel);
        dishModel.setPrice(60000);
        dishModel.setRestaurant(restaurantModel);
        dishModel.setActive(true);
        return dishModel;
    }

    public static UserModel getEmployeeUserModel() {
        UserModel userModel = new UserModel();
        userModel.setId(12L);
        userModel.setName("Camilo");
        userModel.setLastname("Velez");
        userModel.setEmail("email@email.com");
        userModel.setPassword("12345");
        userModel.setPhone("12343534");
        userModel.setRole("EMPLOYEE");
        return userModel;
    }
}
