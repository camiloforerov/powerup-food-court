package com.pragma.powerup.domain.usecase.owner;

import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryOwnerUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UpdateDishUseCaseTest {
    @InjectMocks
    OwnerUseCase ownerUseCase;
    @Mock
    IDishPersistentPort dishPersistentPort;
    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Test
    void mustUpdateDishDescription() {
        String ownerEmail = "owner@mail.com";
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        DishModel dishModel = FactoryOwnerUseCase.getCreatedDishModel(categoryModel, restaurantModel);
        Double price = null;
        String description = "New description";

        when(dishPersistentPort.getDishesByRestaurantId(any()))
                .thenReturn(Collections.singletonList(dishModel));
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);
        when(dishPersistentPort.saveDish(dishModel))
                .thenReturn(dishModel);

        DishModel dishModelUpdated = ownerUseCase.updateDish(dishModel.getId(), price, description, ownerEmail);
        Assertions.assertEquals(dishModelUpdated.getDescription(), description);
    }

    @Test
    void mustUpdateDishPrice() {
        String ownerEmail = "owner@mail.com";
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        DishModel dishModel = FactoryOwnerUseCase.getCreatedDishModel(categoryModel, restaurantModel);
        Double price = 43243D;
        String description = null;

        when(dishPersistentPort.getDishesByRestaurantId(any()))
                .thenReturn(Collections.singletonList(dishModel));
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);
        when(dishPersistentPort.saveDish(dishModel))
                .thenReturn(dishModel);

        DishModel dishModelUpdated = ownerUseCase.updateDish(dishModel.getId(), price, description, ownerEmail);
        Assertions.assertEquals(dishModelUpdated.getPrice(), price);
    }

    @Test
    void throwDishDoesNotExistWhenAttemptSearchWhenUpdating() {
        String ownerEmail = "owner@mail.com";
        Double price = 43243D;
        String description = "New description";
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();

        when(dishPersistentPort.getDishesByRestaurantId(any()))
                .thenReturn(Collections.emptyList());
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);

        Assertions.assertThrows(
                DishDoesNotExistException.class,
                () -> {
                    ownerUseCase.updateDish(1L, price, description, ownerEmail);
                }
        );
    }
}
