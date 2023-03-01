package com.pragma.powerup.domain.usecase.owner;

import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.exceptions.UserDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryAdminUseCase;
import com.pragma.powerup.domain.factory.FactoryOwnerUseCase;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UpdateDishUseCaseTest {
    @InjectMocks
    OwnerUseCase ownerUseCase;

    @Mock
    IDishPersistentPort dishPersistentPort;

    @Test
    void mustUpdateDishDescription() {
        // Given
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        DishModel dishModel = FactoryOwnerUseCase.getCreatedDishModel(categoryModel, restaurantModel);

        Double price = null;
        String description = "New description";
        // When
        when(dishPersistentPort.getDishById(any())).thenReturn(dishModel);
        when(dishPersistentPort.saveDish(dishModel)).thenReturn(dishModel);

        // Then
        DishModel dishModelUpdated = ownerUseCase.updateDish(any(), price, description);
        Assertions.assertEquals(dishModelUpdated.getDescription(), description);
    }

    @Test
    void mustUpdateDishPrice() {
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        DishModel dishModel = FactoryOwnerUseCase.getCreatedDishModel(categoryModel, restaurantModel);

        Double price = 43243D;
        String description = null;
        // When
        when(dishPersistentPort.getDishById(any())).thenReturn(dishModel);
        when(dishPersistentPort.saveDish(dishModel)).thenReturn(dishModel);

        // Then
        DishModel dishModelUpdated = ownerUseCase.updateDish(any(), price, description);
        Assertions.assertEquals(dishModelUpdated.getPrice(), price);
    }

    @Test
    void throwDishDoesNotExistWhenAttemptSearchWhenUpdating() {
        // Given
        Double price = 43243D;
        String description = "New description";
        // When
        when(dishPersistentPort.getDishById(any())).thenReturn(null);

        // Then
        Assertions.assertThrows(
                DishDoesNotExistException.class,
                () -> {
                    ownerUseCase.updateDish(1L, price, description);
                }
        );
    }
}
