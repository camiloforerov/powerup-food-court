package com.pragma.powerup.domain.usecase.owner;

import com.pragma.powerup.domain.exceptions.CategoryDoesNotExistException;
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
public class CreateDishUseCaseTest {

    @InjectMocks
    OwnerUseCase ownerUseCase;

    @Mock
    ICategoryPersistencePort categoryPersistencePort;

    @Mock
    IDishPersistentPort dishPersistentPort;

    @Test
    void mustCreateDishWithActiveState() {
        // Given
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        DishModel baseDishModel = FactoryOwnerUseCase.getBaseDishModel(categoryModel, restaurantModel);

        // When
        when(categoryPersistencePort.getCategoryById(any())).thenReturn(categoryModel);
        when(dishPersistentPort.saveDish(baseDishModel)).thenReturn(baseDishModel);

        // Then
        DishModel dishModelSaved = ownerUseCase.createDish(baseDishModel, restaurantModel, any());
        Assertions.assertTrue(dishModelSaved.isActive());
    }

    @Test
    void throwCategoryDoesNotExistsWhenAttemptToCreateDish() {
        // Given
        CategoryModel categoryModel = FactoryOwnerUseCase.getCategoryModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        DishModel baseDishModel = FactoryOwnerUseCase.getBaseDishModel(categoryModel, restaurantModel);

        // When
        when(categoryPersistencePort.getCategoryById(any())).thenReturn(null);
        // Then
        Assertions.assertThrows(
                CategoryDoesNotExistException.class,
                () -> {
                    ownerUseCase.createDish(baseDishModel, restaurantModel, 1L);
                }
        );
    }
}
