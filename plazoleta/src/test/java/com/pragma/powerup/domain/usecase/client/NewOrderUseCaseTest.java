package com.pragma.powerup.domain.usecase.client;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exceptions.ClientAlreadyHasOrderInProcessException;
import com.pragma.powerup.domain.exceptions.DishesCannotBeEmptyException;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryClientUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.usecase.ClientUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class NewOrderUseCaseTest {
    @InjectMocks
    ClientUseCase clientUseCase;

    @Mock
    IOrderPersistentPort orderPersistentPort;

    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Test
    void mustCreateOrderWithDishes() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryClientUseCase.getCategoryModel();
        DishModel dishModel = FactoryClientUseCase.getDishModel(categoryModel, restaurantModel);
        OrderDishModel orderDishModel = FactoryClientUseCase.getOrderDishModelWithDish(dishModel);
        String clientEmail = "client@mail.com";
        OrderModel orderModel = FactoryClientUseCase.getOrderModel();

        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.of(restaurantModel));
        when(orderPersistentPort.getInProcessOrdersByClientEmail(clientEmail))
                .thenReturn(Collections.emptyList());
        when(orderPersistentPort.saveOrder(orderModel))
                .thenReturn(orderModel);
        when(orderPersistentPort.saveOrderDish(orderDishModel))
                .thenReturn(orderDishModel);

        clientUseCase.newOrder(Collections.singletonList(orderDishModel), restaurantModel.getId(), clientEmail);

        verify(orderPersistentPort).saveOrderDish(any(OrderDishModel.class));
    }

    @Test
    void throwsDishesCannotBeEmptyWhenAttemptCreateNewOrder() {
        List<OrderDishModel> orderDishModels = Collections.emptyList();
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();
        String clientEmail = "client@mail.com";

        Assertions.assertThrows(
                DishesCannotBeEmptyException.class,
                () -> {
                    clientUseCase.newOrder(orderDishModels, restaurantModel.getId(), clientEmail);
                }
        );
    }

    @Test
    void throwsRestaurantDoesNotExistWhenAttemptCreateNewOrder() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryClientUseCase.getCategoryModel();
        DishModel dishModel = FactoryClientUseCase.getDishModel(categoryModel, restaurantModel);
        OrderDishModel orderDishModel = FactoryClientUseCase.getOrderDishModelWithDish(dishModel);
        String clientEmail = "client@mail.com";

        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                RestaurantDoesNotExistException.class,
                () -> {
                    clientUseCase.newOrder(Collections.singletonList(orderDishModel), restaurantModel.getId(), clientEmail);
                }
        );
    }

    @Test
    void throwsClientAlreadyHasOrderInProcessWhenAttemptCreateNewOrder() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryClientUseCase.getCategoryModel();
        DishModel dishModel = FactoryClientUseCase.getDishModel(categoryModel, restaurantModel);
        OrderDishModel orderDishModel = FactoryClientUseCase.getOrderDishModelWithDish(dishModel);
        String clientEmail = "client@mail.com";
        OrderModel orderModel = FactoryClientUseCase.getOrderModel();

        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.of(restaurantModel));
        when(orderPersistentPort.getInProcessOrdersByClientEmail(clientEmail))
                .thenReturn(Collections.singletonList(orderModel));

        Assertions.assertThrows(
                ClientAlreadyHasOrderInProcessException.class,
                () -> {
                    clientUseCase.newOrder(Collections.singletonList(orderDishModel), restaurantModel.getId(), clientEmail);
                }
        );
    }

}
