package com.pragma.powerup.domain.factory;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;

import java.util.Date;

public class FactoryEmployeeUseCase {
    public static RestaurantEmployeeModel getRestaurantEmployeeModel(RestaurantModel restaurantModel) {
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setUserEmail("employee@mail.com");
        restaurantEmployeeModel.setRestaurant(restaurantModel);
        return restaurantEmployeeModel;
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

    public static OrderModel getOrderModel() {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(1L);
        orderModel.setState(Constants.ORDER_PENDING_STATE);
        orderModel.setDate(new Date());
        return orderModel;
    }

    public static OrderDishModel getOrderDishModel(DishModel dishModel, OrderModel orderModel, int amount) {
        OrderDishModel orderDishModel = new OrderDishModel();
        orderDishModel.setOrderModel(orderModel);
        orderDishModel.setDishModel(dishModel);
        orderDishModel.setAmount(amount);
        return orderDishModel;
    }
}
