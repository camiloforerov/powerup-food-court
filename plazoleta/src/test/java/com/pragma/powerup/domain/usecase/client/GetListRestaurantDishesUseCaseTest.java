package com.pragma.powerup.domain.usecase.client;

import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryClientUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.usecase.ClientUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetListRestaurantDishesUseCaseTest {
    @InjectMocks
    ClientUseCase clientUseCase;
    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;
    @Mock
    IDishPersistentPort dishPersistentPort;

    @Test
    void mustReturnDishesGroupedByCategory() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryClientUseCase.getCategoryModel();
        DishModel dishModel = FactoryClientUseCase.getDishModel(categoryModel, restaurantModel);

        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.of(restaurantModel));
        when(dishPersistentPort.listDishesByRestaurantPageable(restaurantModel.getId(), 0, 10))
                .thenReturn(Collections.singletonList(dishModel));

        Assertions.assertInstanceOf(List.class, clientUseCase.listRestaurantDishesCategorized(restaurantModel.getId(), 0, 10));
    }

    @Test
    void returnEmptyListWhenAttemptToGetDishesGroupedByCategoryAndThereIsNoDishes() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();

        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.of(restaurantModel));
        when(dishPersistentPort.listDishesByRestaurantPageable(restaurantModel.getId(), 0, 10))
                .thenReturn(Collections.emptyList());

        Assertions.assertTrue((clientUseCase.listRestaurantDishesCategorized(restaurantModel.getId(), 0, 10)).isEmpty());
    }

    @Test
    void throwsRestaurantDoesNotExistWhenAttemptToGetDishesGroupedByCategory() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();

        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                RestaurantDoesNotExistException.class,
                () -> {
                    clientUseCase.listRestaurantDishesCategorized(restaurantModel.getId(), 0, 10);
                }
        );
    }
}
