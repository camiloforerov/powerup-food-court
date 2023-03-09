package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.OrderWithDishesModel;

import java.util.List;

public interface IEmployeeServicePort {
    List<OrderWithDishesModel> listOrdersByState(String orderState, int page, int elementsPerPage, String employeeEmail);
    OrderWithDishesModel assignOrder(String employeeEmail, Long orderId);
}
