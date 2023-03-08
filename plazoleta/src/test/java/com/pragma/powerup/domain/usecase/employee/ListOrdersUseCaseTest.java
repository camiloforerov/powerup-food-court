package com.pragma.powerup.domain.usecase.employee;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.factory.FactoryEmployeeUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistentPort;
import com.pragma.powerup.domain.usecase.EmployeeUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ListOrdersUseCaseTest {
    @InjectMocks
    EmployeeUseCase employeeUseCase;
    @Mock
    IOrderPersistentPort orderPersistentPort;
    @Mock
    IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;

    @Test
    void mustListOrdersCorrectlyWhenAttemptToListOrdersByState() {
        String orderState = "PENDING";
        int page = 0;
        int elementsPerPage = 10;
        String employeeEmail = "employee@mail.com";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        CategoryModel categoryModel = FactoryEmployeeUseCase.getCategoryModel();
        DishModel dishModel = FactoryEmployeeUseCase.getDishModel(categoryModel, restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setRestaurant(restaurantModel);

        OrderDishModel orderDishModel = FactoryEmployeeUseCase.getOrderDishModel(dishModel, orderModel, 2);

        OrderWithDishesModel orderWithDishesModel = new OrderWithDishesModel();
        orderWithDishesModel.setOrderDishes(Collections.singletonList(orderDishModel));
        orderWithDishesModel.setId(orderModel.getId());

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrdersByRestaurantIdAndState(restaurantModel.getId(),
                page,
                elementsPerPage,
                orderState))
                .thenReturn(Collections.singletonList(orderWithDishesModel));

        List<OrderWithDishesModel> orderWithDishesResponse = employeeUseCase.listOrdersByState(orderState, 0, 10, employeeEmail);
        Assertions.assertInstanceOf(List.class, orderWithDishesResponse);
    }

    @Test
    void throwsEmployeeDoesNotBelongToAnyRestaurantWhenAttemptToListOrdersByState() {
        String employeeEmail = "employee@mail.com";
        String orderState = Constants.ORDER_PENDING_STATE;
        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EmployeeDoesNotBelongToAnyRestaurantException.class,
                () -> {
                    employeeUseCase.listOrdersByState(orderState, 0, 10, employeeEmail);
                }
        );
    }

}
