package com.pragma.powerup.domain.usecase.owner;

import com.pragma.powerup.domain.exceptions.NoRestaurantForOwnerFoundException;
import com.pragma.powerup.domain.factory.FactoryOwnerUseCase;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GetRestaurantOwnerUseCaseTest {
    @InjectMocks
    OwnerUseCase ownerUseCase;

    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Test
    void mustFindRestaurantForOwner() {
        String ownerEmail = "dummy@gmail.com";
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        restaurantModel.setOwnerEmail(ownerEmail);

        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(restaurantModel);

        Assertions.assertEquals(restaurantModel, ownerUseCase.getRestaurantByOwnerEmail(ownerEmail));
    }

    @Test
    void throwRestaurantNotFoundForOwnerEmail() {
        String ownerEmail = "dummy@gmail.com";
        when(restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail))
                .thenReturn(null);
        Assertions.assertThrows(
                NoRestaurantForOwnerFoundException.class,
                () -> {
                    ownerUseCase.getRestaurantByOwnerEmail(ownerEmail);
                }
        );
    }
}
