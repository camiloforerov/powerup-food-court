package com.pragma.powerup.domain.usecase.employee;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderIsAlreadyAssignedToChefException;
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
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AssignOrderUseCaseTest {
    @InjectMocks
    EmployeeUseCase employeeUseCase;
    @Mock
    IOrderPersistentPort orderPersistentPort;
    @Mock
    IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;

    @Test
    void mustAssignOrderAndChangeStateToInPreparation() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_PENDING_STATE);
        CategoryModel categoryModel = FactoryEmployeeUseCase.getCategoryModel();
        DishModel dishModel = FactoryEmployeeUseCase.getDishModel(categoryModel, restaurantModel);

        OrderDishModel orderDishModel = FactoryEmployeeUseCase.getOrderDishModel(dishModel, orderModel, 2);

        OrderWithDishesModel orderWithDishesModel = new OrderWithDishesModel();
        orderWithDishesModel.setOrderDishes(Collections.singletonList(orderDishModel));
        orderWithDishesModel.setId(orderModel.getId());
        orderWithDishesModel.setState(Constants.ORDER_PREPARATION_STATE);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));
        when(orderPersistentPort.saveOrderToOrderWithDishes(orderModel))
                .thenReturn(orderWithDishesModel);

        OrderWithDishesModel order = employeeUseCase.assignOrder(employeeEmail, orderId);
        Assertions.assertEquals(Constants.ORDER_PREPARATION_STATE, order.getState());
    }

    @Test
    void throwsEmployeeDoesNotBelongToAnyRestaurantWhenAttemptToAssignAnOrder() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EmployeeDoesNotBelongToAnyRestaurantException.class,
                () -> {
                    employeeUseCase.assignOrder(employeeEmail, orderId);
                }
        );
    }

    @Test
    void throwsOrderDoesNotExistWhenAttemptToAssignAnOrder() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);


        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                OrderDoesNotExistException.class,
                () -> {
                    employeeUseCase.assignOrder(employeeEmail, orderId);
                }
        );
    }

    @Test
    void throwsOrderIsAlreadyAssignedToAChefWhenAttemptToAssignAnOrder() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);

        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_PENDING_STATE);
        orderModel.setChef(restaurantEmployeeModel);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));

        Assertions.assertThrows(
                OrderIsAlreadyAssignedToChefException.class,
                () -> {
                    employeeUseCase.assignOrder(employeeEmail, orderId);
                }
        );
    }

    @Test
    void throwsOrderIsNotInPendingStateWhenAttemptToAssignAnOrder() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);

        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_DELIVERED_STATE);
        orderModel.setChef(restaurantEmployeeModel);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));

        Assertions.assertThrows(
                OrderIsAlreadyAssignedToChefException.class,
                () -> {
                    employeeUseCase.assignOrder(employeeEmail, orderId);
                }
        );
    }
}
