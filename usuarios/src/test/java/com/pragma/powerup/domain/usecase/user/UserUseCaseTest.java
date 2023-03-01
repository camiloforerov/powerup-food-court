package com.pragma.powerup.domain.usecase.user;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.exception.UserNotFoundException;
import com.pragma.powerup.domain.factory.FactoryAdminUseCase;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserUseCaseTest {

    @InjectMocks
    UserUseCase userUseCase;
    @Mock
    IUserPersistentPort userPersistentPort;
    @Mock
    IRolePersistentPort rolePersistentPort;
    @Test
    void mustGetRestaurantOwner() {
        String roleOwnerName = Constants.ROLE_OWNER_NAME;
        String ownerEmail = "owner@gmail.com";
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        roleModel.setName(roleOwnerName);

        when(userPersistentPort.getUserByEmailAndRole(ownerEmail, roleModel))
                .thenReturn(Optional.of(userModel));
        when(rolePersistentPort.getRoleByName(roleOwnerName))
                .thenReturn(Optional.of(roleModel));

        Assertions.assertEquals(userModel, userUseCase.getRestaurantOwnerByEmail(ownerEmail));
    }

    @Test
    void throwsRoleNotFoundWhenAttemptingToFindUserOwner() {
        String roleOwnerName = Constants.ROLE_OWNER_NAME;
        String ownerEmail = "owner@gmail.com";

        when(rolePersistentPort.getRoleByName(roleOwnerName))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                RoleNotFoundException.class,
                () -> {
                    userUseCase.getRestaurantOwnerByEmail(ownerEmail);
                }
        );
    }

    @Test
    void throwsUserNotFoundWhenAttemptingToFindUserOwner() {
        String roleOwnerName = Constants.ROLE_OWNER_NAME;
        String ownerEmail = "owner@gmail.com";
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        roleModel.setName(roleOwnerName);

        when(rolePersistentPort.getRoleByName(roleOwnerName))
                .thenReturn(Optional.of(roleModel));
        when(userPersistentPort.getUserByEmailAndRole(ownerEmail, roleModel))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> {
                    userUseCase.getRestaurantOwnerByEmail(ownerEmail);
                }
        );
    }
}