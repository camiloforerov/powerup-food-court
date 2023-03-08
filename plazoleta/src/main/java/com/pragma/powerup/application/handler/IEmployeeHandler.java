package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.response.OrderResponseDto;

import java.util.List;

public interface IEmployeeHandler {
    List<OrderResponseDto> listOrdersByState(String orderState, int page, int elementsPerPage);
}
