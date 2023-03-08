package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EmployeeUseCase implements IEmployeeServicePort {
    private final IOrderPersistentPort orderPersistentPort;
    private final IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;

    /**
     * Gets the orders from employee's restaurant filtered by the specified state
     *
     * @param orderState - Must be already validated
     * @param page - page number to show. For pagination
     * @param elementsPerPage - elements to show per page. For pagination
     * @return list of orders with its dishes
     * */
    @Override
    public List<OrderWithDishesModel> listOrdersByState(String orderState, int page, int elementsPerPage, String employeeEmail) {
        Optional<RestaurantEmployeeModel> employeeModel = this.restaurantEmployeePersistentPort
                .findByEmployeeEmail(employeeEmail);
        if (employeeModel.isEmpty()) {
            throw new EmployeeDoesNotBelongToAnyRestaurantException("Employee doesn't have a restaurant associated");
        }

        return this.orderPersistentPort
                .getOrdersByRestaurantIdAndState(employeeModel.get().getRestaurant().getId(),
                        page,
                        elementsPerPage,
                        orderState);
    }
}
