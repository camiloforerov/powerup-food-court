package com.pragma.powerup.domain.usecase.client;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderStateCannotChangeException;
import com.pragma.powerup.domain.factory.FactoryClientUseCase;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IMessagingServicePort;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.usecase.ClientUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CancelOrderUseCaseTest {
    @InjectMocks
    ClientUseCase clientUseCase;
    @Mock
    IOrderPersistentPort orderPersistentPort;
    @Mock
    IUserServicePort userServicePort;
    @Mock
    IMessagingServicePort messagingServicePort;

    @Test
    void mustCancelOrderCorrectly() {
        Long orderId = 1L;
        OrderModel orderModel = FactoryClientUseCase.getOrderModel();
        String clientEmail = "client@mail.com";
        orderModel.setState(Constants.ORDER_PENDING_STATE);
        orderModel.setClientEmail(clientEmail);

        when(orderPersistentPort.getOrderById(orderId))
                .thenReturn(Optional.of(orderModel));

        clientUseCase.cancelOrder(orderId, clientEmail);

        verify(orderPersistentPort).saveOrder(argThat(arg -> arg.getState().equals(Constants.ORDER_CANCELED_STATE)));
        verify(orderPersistentPort).saveOrder(orderModel);
    }

    @Test
    void throwsOrderDoesNotExistsExceptionWhenAttemptToCancelAnOrder() {
        Long orderId = 1L;
        OrderModel orderModel = FactoryClientUseCase.getOrderModel();
        String clientEmail = "client@mail.com";
        orderModel.setState(Constants.ORDER_PENDING_STATE);
        orderModel.setClientEmail(clientEmail);

        when(orderPersistentPort.getOrderById(orderId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                OrderDoesNotExistException.class,
                () -> {
                    clientUseCase.cancelOrder(orderId, clientEmail);
                }
        );
    }

    @Test
    void throwsOrderStateCannotChangeExceptionWhenAttemptToCancelAnOrder() {
        Long orderId = 1L;
        OrderModel orderModel = FactoryClientUseCase.getOrderModel();
        String clientEmail = "client@mail.com";
        orderModel.setState(Constants.ORDER_READY_STATE);
        orderModel.setClientEmail(clientEmail);

        String message = "Message";
        String phone = "+573225353338";

        UserModel userModel = FactoryClientUseCase.getClientUserModel();

        when(orderPersistentPort.getOrderById(orderId))
                .thenReturn(Optional.of(orderModel));
        when(userServicePort.getUserByEmail(clientEmail))
                .thenReturn(userModel);
        when(messagingServicePort.notifyClient(message, phone))
                .thenReturn(true);

        Assertions.assertThrows(
                OrderStateCannotChangeException.class,
                () -> {
                    clientUseCase.cancelOrder(orderId, clientEmail);
                }
        );
    }
}
