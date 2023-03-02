package com.pragma.powerup.domain.usecase.admin;

import com.pragma.powerup.domain.exceptions.OwnerAlreadyHasRestaurantException;
import com.pragma.powerup.domain.exceptions.UserDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryAdminUseCase;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.usecase.AdminUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdminUseCaseTest {
    @InjectMocks
    AdminUseCase adminUseCase;
    @Mock
    IUserServicePort userServicePort;
    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Test
    void mustCreateRestaurantCorrectly() {
        String ownerEmail = "owner@mail.com";
        RestaurantModel restaurantModel = FactoryAdminUseCase.getRestaurantModel();

        when(userServicePort.userIsRestaurantOwner(restaurantModel.getOwnerEmail()))
                .thenReturn(true);
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(null);

        adminUseCase.createRestaurant(restaurantModel);
        verify(restaurantPersistentPort).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void throwsUserDoesNotExistsWhenAttemptToCreateRestaurant() {
        String ownerEmail = "owner@mail.com";
        RestaurantModel restaurantModel = FactoryAdminUseCase.getRestaurantModel();

        when(userServicePort.userIsRestaurantOwner(restaurantModel.getOwnerEmail()))
                .thenReturn(false);
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(null);

        Assertions.assertThrows(
                UserDoesNotExistException.class,
                () -> {
                    adminUseCase.createRestaurant(restaurantModel);
                }
        );
    }

    @Test
    void throwsOwnerAlreadyHasRestaurantWhenAttemptToCreateRestaurant() {
        String ownerEmail = "owner@mail.com";
        RestaurantModel restaurantModel = FactoryAdminUseCase.getRestaurantModel();
        RestaurantModel restaurantModelExistent = FactoryAdminUseCase.getRestaurantModel();
        restaurantModel.setOwnerEmail(ownerEmail);

        when(userServicePort.userIsRestaurantOwner(restaurantModel.getOwnerEmail()))
                .thenReturn(true);
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModelExistent);

        Assertions.assertThrows(
                OwnerAlreadyHasRestaurantException.class,
                () -> {
                    adminUseCase.createRestaurant(restaurantModel);
                }
        );
    }
}