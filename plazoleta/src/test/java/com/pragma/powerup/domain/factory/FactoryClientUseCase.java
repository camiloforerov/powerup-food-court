package com.pragma.powerup.domain.factory;

import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;

import java.util.Date;

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

    public static OrderDishModel getOrderDishModelWithDish(DishModel dishModel) {
        OrderDishModel orderDishModel = new OrderDishModel();
        orderDishModel.setDishModel(dishModel);
        orderDishModel.setAmount(2);
        return orderDishModel;
    }

    public static OrderModel getOrderModel() {
        OrderModel orderModel = new OrderModel();
        orderModel.setDate(new Date());
        return orderModel;
    }

    public static UserModel getClientUserModel() {
        UserModel userModel = new UserModel();
        userModel.setId(12L);
        userModel.setName("Camilo");
        userModel.setLastname("Velez");
        userModel.setEmail("client@email.com");
        userModel.setPassword("12345");
        userModel.setPhone("12343534");
        userModel.setRole("CLIENT");
        return userModel;
    }
}
