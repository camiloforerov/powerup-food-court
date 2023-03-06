package com.pragma.powerup.domain.usecase.client;

import com.pragma.powerup.domain.factory.FactoryClientUseCase;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.usecase.ClientUseCase;
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
class GetListRestaurantsUseCaseTest {
    @InjectMocks
    ClientUseCase clientUseCase;

    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Test
    void mustReturnListOfRestaurantsWhenListingAllRestaurants() {
        RestaurantModel restaurantModel = FactoryClientUseCase.getRestaurantModel();

        when(restaurantPersistentPort.listByPageAndElements(0, 10))
                .thenReturn(Collections.singletonList(restaurantModel));

        Assertions.assertInstanceOf(List.class, clientUseCase.listRestaurants(0, 10));
    }
}
