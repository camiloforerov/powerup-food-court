package com.pragma.powerup.domain.usecase.employee;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.exceptions.NotificationNotSent;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderStateCannotChangeException;
import com.pragma.powerup.domain.factory.FactoryEmployeeUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IMessagingServicePort;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.usecase.EmployeeUseCase;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ChangeOrderReadyUseCaseTest {
    @InjectMocks
    EmployeeUseCase employeeUseCase;
    @Mock
    IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;
    @Mock
    IOrderPersistentPort orderPersistentPort;
    @Mock
    IUserServicePort userServicePort;
    @Mock
    IMessagingServicePort messagingServicePort;

    @Test
    void mustChangeOrderStateWhenAttemptToChangeToReady() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        String clientEmail = "client@mail.com";
        String clientPhoneNumber = "+573225353338";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_PREPARATION_STATE);
        orderModel.setClientEmail(clientEmail);
        CategoryModel categoryModel = FactoryEmployeeUseCase.getCategoryModel();
        DishModel dishModel = FactoryEmployeeUseCase.getDishModel(categoryModel, restaurantModel);

        OrderDishModel orderDishModel = FactoryEmployeeUseCase.getOrderDishModel(dishModel, orderModel, 2);

        OrderWithDishesModel orderWithDishesModel = new OrderWithDishesModel();
        orderWithDishesModel.setOrderDishes(Collections.singletonList(orderDishModel));
        orderWithDishesModel.setId(orderModel.getId());
        orderWithDishesModel.setState(Constants.ORDER_PREPARATION_STATE);

        UserModel userModel = new UserModel();
        userModel.setPhone(clientPhoneNumber);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));
        when(orderPersistentPort.getOrdersReadyBySecurityCode(anyString()))
                .thenReturn(Collections.emptyList());
        when(orderPersistentPort.saveOrder(orderModel))
                .thenReturn(orderModel);
        when(userServicePort.getUserByEmail(orderModel.getClientEmail()))
                .thenReturn(userModel);
        when(messagingServicePort.notifyClient(anyString(), eq(clientPhoneNumber)))
                .thenReturn(true);

        employeeUseCase.changeOrderToReady(orderId, employeeEmail);

        verify(orderPersistentPort).saveOrder(argThat(arg -> arg.getState().equals(Constants.ORDER_READY_STATE)));
        verify(orderPersistentPort).saveOrder(orderModel);
    }

    @Test
    void throwsEmployeeDoesNotBelongToAnyRestaurantWhenAttemptToChangeOrderToReady() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EmployeeDoesNotBelongToAnyRestaurantException.class,
                () -> {
                    employeeUseCase.changeOrderToReady(orderId, employeeEmail);
                }
        );
    }

    @Test
    void throwsOrderDoesNotExistWhenAttemptToChangeOrderToReady() {
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
                    employeeUseCase.changeOrderToReady(orderId, employeeEmail);
                }
        );
    }

    @Test
    void throwsOrderStateCannotChangeExceptionWhenAttemptToChangeOrderToReady() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        String clientEmail = "client@mail.com";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_PENDING_STATE);
        orderModel.setClientEmail(clientEmail);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));
        when(orderPersistentPort.getOrdersReadyBySecurityCode(anyString()))
                .thenReturn(Collections.emptyList());
        when(orderPersistentPort.saveOrder(orderModel))
                .thenReturn(orderModel);

        Assertions.assertThrows(
                OrderStateCannotChangeException.class,
                () -> {
                    employeeUseCase.changeOrderToReady(orderId, employeeEmail);
                }
        );
    }

    @Test
    void throwsNotificationNotSentExceptionWhenAttemptToChangeOrderToReady() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        String clientEmail = "client@mail.com";
        String clientPhoneNumber = "+573225353338";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_PREPARATION_STATE);
        orderModel.setClientEmail(clientEmail);
        CategoryModel categoryModel = FactoryEmployeeUseCase.getCategoryModel();
        DishModel dishModel = FactoryEmployeeUseCase.getDishModel(categoryModel, restaurantModel);

        OrderDishModel orderDishModel = FactoryEmployeeUseCase.getOrderDishModel(dishModel, orderModel, 2);

        OrderWithDishesModel orderWithDishesModel = new OrderWithDishesModel();
        orderWithDishesModel.setOrderDishes(Collections.singletonList(orderDishModel));
        orderWithDishesModel.setId(orderModel.getId());
        orderWithDishesModel.setState(Constants.ORDER_PREPARATION_STATE);

        UserModel userModel = new UserModel();
        userModel.setPhone(clientPhoneNumber);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));
        when(orderPersistentPort.getOrdersReadyBySecurityCode(anyString()))
                .thenReturn(Collections.emptyList());
        when(orderPersistentPort.saveOrder(orderModel))
                .thenReturn(orderModel);
        when(userServicePort.getUserByEmail(orderModel.getClientEmail()))
                .thenReturn(userModel);
        when(messagingServicePort.notifyClient(anyString(), eq(clientPhoneNumber)))
                .thenReturn(false);

        Assertions.assertThrows(
                NotificationNotSent.class,
                () -> {
                    employeeUseCase.changeOrderToReady(orderId, employeeEmail);
                }
        );
    }

}
