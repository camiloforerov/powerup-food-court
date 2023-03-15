package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.AssignOrderResponseDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;

import java.util.List;

public interface IEmployeeHandler {
    List<OrderResponseDto> listOrdersByState(String orderState, int page, int elementsPerPage);
    List<AssignOrderResponseDto> assignOrdersToEmployee(List<Long> ordersId);
    void changeOrderToReady(Long orderId);
    void changeOrderToDelivered(Long orderId, String securityCode);
}
