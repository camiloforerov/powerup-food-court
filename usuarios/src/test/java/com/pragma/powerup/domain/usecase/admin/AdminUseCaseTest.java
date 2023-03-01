package com.pragma.powerup.domain.usecase.admin;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IAuthPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.factory.FactoryAdminUseCase;
import com.pragma.powerup.domain.usecase.AdminUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AdminUseCaseTest {
    @InjectMocks
    AdminUseCase adminUseCase;
    @Mock
    IRolePersistentPort roleServicePort;
    @Mock
    IUserPersistentPort userServicePort;
    @Mock
    IAuthPasswordEncoderPort authServicePort;

    @Test
    void mustCreateRestaurantOwner() {
        // Given
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        userModel.setRole(roleModel);
        // When
        when(roleServicePort.getRoleByName("ROLE_OWNER")).thenReturn(Optional.of(roleModel));
        when(authServicePort.encodePassword("4321")).thenReturn("43234123124324123");
        when(userServicePort.getUserByEmail("nonexistent@email.com")).thenReturn(null);

        adminUseCase.createRestaurantOwner(userModel);
        // Then
        verify(userServicePort).saveUser(any(UserModel.class));
    }

    @Test
    void throwRoleNotExistWhenAttemptSaveOwner() {
        // Given
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        roleModel.setName("non existent");
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        userModel.setRole(roleModel);

        // When
        when(roleServicePort.getRoleByName("non existent")).thenReturn(null);

        // Then
        Assertions.assertThrows(
                RoleNotFoundException.class,
                () -> {
                    adminUseCase.createRestaurantOwner(userModel);
                }
        );
    }

    @Test
    void throwUserAlreadyExistWhenAttemptSaveOwner() {
        // Given
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        userModel.setRole(roleModel);

        // When
        when(roleServicePort.getRoleByName("ROLE_OWNER")).thenReturn(Optional.of(roleModel));
        when(userServicePort.getUserByEmail(any())).thenReturn(Optional.of(userModel));

        // Then
        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> {
                    adminUseCase.createRestaurantOwner(userModel);
                }
        );

    }
}