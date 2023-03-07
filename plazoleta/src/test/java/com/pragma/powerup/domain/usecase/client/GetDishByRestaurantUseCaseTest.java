package com.pragma.powerup.domain.usecase.client;

import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryClientUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.domain.usecase.ClientUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GetDishByRestaurantUseCaseTest {
    @InjectMocks
    ClientUseCase clientUseCase;

    @Mock
    IDishPersistentPort dishPersistentPort;

    @Test
    void mustFindDishAndReturnIt() {
        Long dishId = 1L;
        Long restaurantId = 1L;
        CategoryModel categoryModel = FactoryClientUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();
        DishModel dishModel = FactoryClientUseCase.getDishModel(categoryModel, restaurantModel);
        dishModel.setId(dishId);

        when(dishPersistentPort.getDishByIdAndRestaurantId(dishId, restaurantId))
                .thenReturn(Optional.of(dishModel));

        DishModel foundDishModel = clientUseCase.getDishModelByIdAndRestaurantId(dishId, restaurantId);

        Assertions.assertEquals(dishId, foundDishModel.getId());
    }

    @Test
    void throwsDishDoesNotExistsWhenAttemptToGetTheDish() {
        Long dishId = 1L;
        Long restaurantId = 1L;

        when(dishPersistentPort.getDishByIdAndRestaurantId(dishId, restaurantId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                DishDoesNotExistException.class,
                () -> {
                    clientUseCase.getDishModelByIdAndRestaurantId(dishId, restaurantId);
                }
        );
    }

}
