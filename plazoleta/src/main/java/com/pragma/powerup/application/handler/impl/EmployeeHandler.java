package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.exception.exception.ServerErrorException;
import com.pragma.powerup.application.handler.IEmployeeHandler;
import com.pragma.powerup.application.mapper.IListOrdersResponseMapper;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeHandler implements IEmployeeHandler {
    private final IEmployeeServicePort employeeServicePort;
    private final IListOrdersResponseMapper listOrdersResponseMapper;

    /**
     * Lists orders by state
     *
     * @param orderState - order state to filter
     * @param page - page number to show. For pagination
     * @param elementsPerPage - elements to show per page. For pagination
     * @return list of orders with its dishes
     * */
    @Override
    public List<OrderResponseDto> listOrdersByState(String orderState, int page, int elementsPerPage) {
        String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderWithDishesModel> orders;
        try {
             orders = this.employeeServicePort.listOrdersByState(orderState, page, elementsPerPage, ownerEmail);
        } catch (EmployeeDoesNotBelongToAnyRestaurantException ex) {
            throw new ServerErrorException(ex.getMessage());
        }

        return orders.stream()
                .map(listOrdersResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}
