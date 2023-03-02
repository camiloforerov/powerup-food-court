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
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UpdateDishStateUseCaseTest {
    @InjectMocks
    OwnerUseCase ownerUseCase;
    @Mock
    IDishPersistentPort dishPersistentPort;
    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Test
    void mustUpdateDishStateToActive() {
        boolean state = true;
        String ownerEmail = "owner@gmail.com";
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        DishModel dishModel = FactoryOwnerUseCase
                .getCreatedDishModel(categoryModel, restaurantModel);
        dishModel.setActive(false);
        List<DishModel> dishes = Collections.singletonList(dishModel);

        when(dishPersistentPort.getDishesByRestaurantId(restaurantModel.getId()))
                .thenReturn(dishes);
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);
        when(dishPersistentPort.saveDish(dishModel))
                .thenReturn(dishModel);

        DishModel updatedDishModel = ownerUseCase.updateDishState(dishModel.getId(), state, ownerEmail);
        Assertions.assertTrue(updatedDishModel.isActive());
    }

    @Test
    void mustUpdateDishStateToInactive() {
        boolean state = false;
        String ownerEmail = "owner@gmail.com";
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        DishModel dishModel = FactoryOwnerUseCase
                .getCreatedDishModel(categoryModel, restaurantModel);
        dishModel.setActive(state);
        List<DishModel> dishes = Collections.singletonList(dishModel);

        when(dishPersistentPort.getDishesByRestaurantId(restaurantModel.getId()))
                .thenReturn(dishes);
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);
        when(dishPersistentPort.saveDish(dishModel))
                .thenReturn(dishModel);

        DishModel updatedDishModel = ownerUseCase.updateDishState(dishModel.getId(), state, ownerEmail);
        Assertions.assertFalse(updatedDishModel.isActive());
    }

    @Test
    void throwsDishDoesNotExistsWhenAttemptUpdating() {
        String ownerEmail = "owner@gmail.com";
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        DishModel dishModel = FactoryOwnerUseCase
                .getCreatedDishModel(categoryModel, restaurantModel);
        List<DishModel> dishes = Collections.emptyList();

        when(dishPersistentPort.getDishesByRestaurantId(restaurantModel.getId()))
                .thenReturn(dishes);
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);
        when(dishPersistentPort.saveDish(dishModel))
                .thenReturn(dishModel);

        Assertions.assertThrows(
                DishDoesNotExistException.class,
                () -> {
                    ownerUseCase.updateDishState(dishModel.getId(), true, ownerEmail);
                }
        );
    }
}
