package com.pragma.powerup.domain.usecase.owner;

import com.pragma.powerup.domain.exceptions.NoRestaurantForOwnerFoundException;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.factory.FactoryOwnerUseCase;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreateEmployeeUseCaseTest {

    @InjectMocks
    OwnerUseCase ownerUseCase;

    @Mock
    IRestaurantPersistentPort restaurantPersistentPort;

    @Mock
    IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;

    @Mock
    IUserServicePort userServicePort;

    @Test
    void mustCreateUserWithEmployeeRole() {
        // Given
        UserModel userModel = FactoryOwnerUseCase.getEmployeeUserModel();
        RestaurantModel restaurantModel = FactoryOwnerUseCase.getRestaurantModel();
        RestaurantEmployeeModel restaurantEmployeeModel = new RestaurantEmployeeModel();
        restaurantEmployeeModel.setUserEmail("");
        restaurantEmployeeModel.setRestaurant(restaurantModel);

        // When
        when(restaurantPersistentPort.getRestaurantById(restaurantModel.getId()))
                .thenReturn(Optional.of(restaurantModel));
        when(userServicePort.createEmployee(userModel, 1L)).thenReturn(userModel);
        when(restaurantEmployeePersistentPort.save(any())).thenReturn(restaurantEmployeeModel);
        // Then

        ownerUseCase.createEmployee(userModel, 1L, restaurantModel.getId());
        verify(restaurantEmployeePersistentPort).save(any(RestaurantEmployeeModel.class));
    }

    @Test
    void throwsRestaurantDoesNotExistWhenAttemptingCreateEmployee() {
        when(restaurantPersistentPort.getRestaurantById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                RestaurantDoesNotExistException.class,
                () -> {
                    ownerUseCase.createEmployee(FactoryOwnerUseCase.getEmployeeUserModel(), anyLong(), 1L);
                }
        );
    }
}
