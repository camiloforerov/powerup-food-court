package com.pragma.powerup.domain.usecase.client;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.factory.FactoryAdminUseCase;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IAuthPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.domain.usecase.ClientUseCase;
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
public class CreateClientUseCaseTest {
    @InjectMocks
    ClientUseCase clientUseCase;
    @Mock
    IUserPersistentPort userPersistentPort;
    @Mock
    IRolePersistentPort rolePersistentPort;
    @Mock
    IAuthPasswordEncoderPort authPasswordEncoderPort;

    @Test
    void mustCreateClient() {
        // Given
        String clientRoleName = Constants.ROLE_CLIENT_NAME;
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        userModel.setRole(roleModel);
        // When
        when(rolePersistentPort.getRoleByName(clientRoleName)).thenReturn(Optional.of(roleModel));
        when(authPasswordEncoderPort.encodePassword("4321")).thenReturn("43234123124324123");
        when(userPersistentPort.getUserByEmail("nonexistent@email.com")).thenReturn(null);

        clientUseCase.createClientAccount(userModel);
        // Then
        verify(userPersistentPort).saveUser(any(UserModel.class));
    }

    @Test
    void throwRoleNotExistWhenAttemptSaveClient() {
        String clientRoleName = Constants.ROLE_CLIENT_NAME;
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        roleModel.setName(clientRoleName);
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        userModel.setRole(roleModel);

        when(rolePersistentPort.getRoleByName(clientRoleName))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                RoleNotFoundException.class,
                () -> {
                    clientUseCase.createClientAccount(userModel);
                }
        );
    }

    @Test
    void throwUserAlreadyExistWhenAttemptSaveOwner() {
        String clientRoleName = Constants.ROLE_CLIENT_NAME;
        RoleModel roleModel = FactoryAdminUseCase.getRoleModel();
        UserModel userModel = FactoryAdminUseCase.getUserModel();
        userModel.setRole(roleModel);

        when(rolePersistentPort.getRoleByName(clientRoleName)).thenReturn(Optional.of(roleModel));
        when(userPersistentPort.getUserByEmail(any())).thenReturn(Optional.of(userModel));

        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> {
                    clientUseCase.createClientAccount(userModel);
                }
        );

    }
}
