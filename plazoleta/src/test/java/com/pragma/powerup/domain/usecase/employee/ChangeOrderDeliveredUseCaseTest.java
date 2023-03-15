package com.pragma.powerup.domain.usecase.employee;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.exceptions.NotificationNotSent;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderStateCannotChangeException;
import com.pragma.powerup.domain.exceptions.SecurityCodeIncorrectException;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ChangeOrderDeliveredUseCaseTest {
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
        String securityPin = "123456";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_READY_STATE);
        orderModel.setClientEmail(clientEmail);
        orderModel.setSecurityPin(securityPin);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));
        when(orderPersistentPort.saveOrder(orderModel))
                .thenReturn(orderModel);

        employeeUseCase.changeOrderToDelivered(orderId, employeeEmail, securityPin);

        verify(orderPersistentPort).saveOrder(argThat(arg -> arg.getState().equals(Constants.ORDER_DELIVERED_STATE)));
        verify(orderPersistentPort).saveOrder(orderModel);
    }

    @Test
    void throwsEmployeeDoesNotBelongToAnyRestaurantWhenAttemptToChangeOrderToDelivered() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                EmployeeDoesNotBelongToAnyRestaurantException.class,
                () -> {
                    employeeUseCase.changeOrderToDelivered(orderId, employeeEmail, anyString());
                }
        );
    }

    @Test
    void throwsOrderDoesNotExistWhenAttemptToChangeOrderToDelivered() {
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
                    employeeUseCase.changeOrderToDelivered(orderId, employeeEmail, anyString());
                }
        );
    }

    @Test
    void throwsOrderStateCannotChangeExceptionWhenAttemptToChangeOrderToDelivered() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        String clientEmail = "client@mail.com";
        String securityCode = "123456";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_PREPARATION_STATE);
        orderModel.setClientEmail(clientEmail);
        orderModel.setSecurityPin(securityCode);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));

        Assertions.assertThrows(
                OrderStateCannotChangeException.class,
                () -> {
                    employeeUseCase.changeOrderToDelivered(orderId, employeeEmail, securityCode);
                }
        );
    }
    @Test
    void throwsSecurityCodeIncorrectExceptionWhenAttemptToChangeOrderToDelivered() {
        String employeeEmail = "employee@mail.com";
        Long orderId = 1L;
        String clientEmail = "client@mail.com";
        String securityCode = "123456";
        RestaurantModel restaurantModel = FactoryEmployeeUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = FactoryEmployeeUseCase
                .getRestaurantEmployeeModel(restaurantModel);
        OrderModel orderModel = FactoryEmployeeUseCase.getOrderModel();
        orderModel.setId(orderId);
        orderModel.setState(Constants.ORDER_READY_STATE);
        orderModel.setClientEmail(clientEmail);
        orderModel.setSecurityPin(securityCode);

        when(restaurantEmployeePersistentPort.findByEmployeeEmail(employeeEmail))
                .thenReturn(Optional.of(restaurantEmployeeModel));
        when(orderPersistentPort.getOrderByRestaurantIdAndOrderId(restaurantModel.getId(), orderId))
                .thenReturn(Optional.of(orderModel));

        Assertions.assertThrows(
                SecurityCodeIncorrectException.class,
                () -> {
                    employeeUseCase.changeOrderToDelivered(orderId, employeeEmail, "");
                }
        );
    }
}
