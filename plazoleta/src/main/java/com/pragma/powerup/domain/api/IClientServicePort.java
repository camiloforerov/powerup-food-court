package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.CategoryWithDishesModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.RestaurantModel;

import java.util.List;

public interface IClientServicePort {
    List<RestaurantModel> listRestaurants(int pageNumber, int numbersOfElements);
    List<CategoryWithDishesModel> listRestaurantDishesCategorized(Long restaurantId, int pageNumber, int elementsPerPage);
    List<OrderDishModel> newOrder(List<OrderDishModel> dishesOrderData,
                                  Long restaurantId, String clientEmail);
    DishModel getDishModelByIdAndRestaurantId(Long dishId, Long restaurantId);
    void cancelOrder(Long orderId, String clientEmail);
}
