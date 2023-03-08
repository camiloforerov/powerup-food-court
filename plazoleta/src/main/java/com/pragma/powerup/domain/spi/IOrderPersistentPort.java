package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;

import java.util.List;
import java.util.Optional;

public interface IOrderPersistentPort {
    List<OrderModel> getInProcessOrdersByClientEmail(String email);
    OrderDishModel saveOrderDish(OrderDishModel orderDishModel);
    OrderModel saveOrder(OrderModel orderModel);
    OrderWithDishesModel saveOrderToOrderWithDishes(OrderModel orderModel);
    List<OrderWithDishesModel> getOrdersByRestaurantIdAndState(Long restaurantId, int page,
                                                               int elementsPerPage, String state);
    Optional<OrderModel> getOrderByRestaurantIdAndOrderId(Long restaurantId, Long orderId);
}