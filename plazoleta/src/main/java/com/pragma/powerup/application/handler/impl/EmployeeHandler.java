package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.AssignOrderResponseDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.exception.exception.BadRequestException;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.exception.exception.ServerErrorException;
import com.pragma.powerup.application.handler.IEmployeeHandler;
import com.pragma.powerup.application.mapper.IAssignOrderResponseMapper;
import com.pragma.powerup.application.mapper.IListOrdersResponseMapper;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.exceptions.NotificationNotSent;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderIsAlreadyAssignedToChefException;
import com.pragma.powerup.domain.exceptions.OrderStateCannotChangeException;
import com.pragma.powerup.domain.exceptions.SecurityCodeIncorrectException;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeHandler implements IEmployeeHandler {
    private final IEmployeeServicePort employeeServicePort;
    private final IListOrdersResponseMapper listOrdersResponseMapper;
    private final IAssignOrderResponseMapper assignOrderResponseMapper;

    /**
     * Lists orders by state
     *
     * @param orderState      - order state to filter
     * @param page            - page number to show. For pagination
     * @param elementsPerPage - elements to show per page. For pagination
     * @return list of orders with its dishes
     */
    @Override
    public List<OrderResponseDto> listOrdersByState(String orderState, int page, int elementsPerPage) {
        String employeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderWithDishesModel> orders;
        try {
            orders = this.employeeServicePort.listOrdersByState(orderState, page, elementsPerPage, employeeEmail);
        } catch (EmployeeDoesNotBelongToAnyRestaurantException ex) {
            throw new ServerErrorException(ex.getMessage());
        }

        return orders.stream()
                .map(listOrdersResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Assign multiple orders to an employee
     *
     * @param ordersId - list of ids corresponding to orders
     * @return list of assigned orders
     * @throws ServerErrorException - employee is not related to any restaurant
     * @throws NoDataFoundException - some order doesn't exist
     * @throws BadRequestException  - the orders is already taken or not in the correct state
     */
    @Override
    public List<AssignOrderResponseDto> assignOrdersToEmployee(List<Long> ordersId) {
        String employeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderWithDishesModel> modifiedOrders = new ArrayList<>();
        for (Long orderId : ordersId) {
            try {
                OrderWithDishesModel orderModel = this.employeeServicePort.assignOrder(employeeEmail, orderId);
                modifiedOrders.add(orderModel);
            } catch (EmployeeDoesNotBelongToAnyRestaurantException ex) {
                throw new ServerErrorException(ex.getMessage());
            } catch (OrderDoesNotExistException ex) {
                throw new NoDataFoundException(ex.getMessage());
            } catch (OrderIsAlreadyAssignedToChefException ex) {
                throw new BadRequestException(ex.getMessage());
            }
        }
        return modifiedOrders.stream()
                .map(assignOrderResponseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Changes the order to ready with the order id
     *
     * @param orderId - order id
     * */
    @Override
    public void changeOrderToReady(Long orderId) {
        String employeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            this.employeeServicePort.changeOrderToReady(orderId, employeeEmail);
        } catch (EmployeeDoesNotBelongToAnyRestaurantException | NotificationNotSent ex) {
            throw new ServerErrorException(ex.getMessage());
        } catch (OrderDoesNotExistException ex) {
            throw new NoDataFoundException(ex.getMessage());
        } catch (OrderStateCannotChangeException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    /**
     * Changes the order to 'delivered' with the order id
     *
     * @param orderId order id
     * */
    @Override
    public void changeOrderToDelivered(Long orderId, String securityCode) {
        String employeeEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            this.employeeServicePort.changeOrderToDelivered(orderId, employeeEmail, securityCode);
        } catch (EmployeeDoesNotBelongToAnyRestaurantException | NotificationNotSent ex) {
            throw new ServerErrorException(ex.getMessage());
        } catch (OrderDoesNotExistException ex) {
            throw new NoDataFoundException(ex.getMessage());
        } catch (OrderStateCannotChangeException | SecurityCodeIncorrectException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}