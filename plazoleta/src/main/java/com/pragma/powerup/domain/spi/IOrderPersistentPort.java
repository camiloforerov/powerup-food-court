package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;

import java.util.List;

public interface IOrderPersistentPort {
    List<OrderModel> getInProcessOrdersByClientEmail(String email);
    OrderDishModel saveOrderDish(OrderDishModel orderDishModel);
    OrderModel saveOrder(OrderModel orderModel);
}