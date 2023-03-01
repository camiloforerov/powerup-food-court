package com.pragma.powerup.domain.usecase.owner;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.factory.FactoryOwnerUseCase;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IAuthPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreateEmployeeUseCaseTest {

    @InjectMocks
    OwnerUseCase ownerUseCase;

    @Mock
    IRolePersistentPort rolePersistentPort;

    @Mock
    IUserPersistentPort userPersistentPort;

    @Mock
    IAuthPasswordEncoderPort authPasswordEncoderPort;

    @Test
    void mustCreateOwnerUserWithEmployeeRole() {
        String roleEmployeeName = Constants.ROLE_EMPLOYEE_NAME;
        String employeeEmail = "employee@gmail.com";
        UserModel userModel = FactoryOwnerUseCase.getUserModel();
        RoleModel roleModel = FactoryOwnerUseCase.getRoleEmployeeModel();

        when(rolePersistentPort.getRoleByName(roleEmployeeName))
                .thenReturn(Optional.of(roleModel));
        when(userPersistentPort.getUserByEmail(employeeEmail))
                .thenReturn(Optional.empty());
        when(authPasswordEncoderPort.encodePassword(userModel.getPassword()))
                .thenReturn("encrypted-password");
        when(userPersistentPort.saveUser(userModel))
                .thenReturn(userModel);

        UserModel createdUserModel = ownerUseCase.createEmployee(userModel);

        Assertions.assertEquals(roleModel, createdUserModel.getRole());
    }

    @Test
    void throwsRoleNotFoundWhenAttemptToCreateEmployee() {
        String roleEmployeeName = Constants.ROLE_EMPLOYEE_NAME;
        UserModel userModel = FactoryOwnerUseCase.getUserModel();

        when(rolePersistentPort.getRoleByName(roleEmployeeName))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                RoleNotFoundException.class,
                () -> {
                    ownerUseCase.createEmployee(userModel);
                }
        );
    }

    @Test
    void throwsEmailAlreadyExistsWhenAttemptCreateEmployee() {
        String roleEmployeeName = Constants.ROLE_EMPLOYEE_NAME;
        String employeeEmail = "employee@gmail.com";
        UserModel userModel = FactoryOwnerUseCase.getUserModel();
        userModel.setEmail(employeeEmail);
        RoleModel roleModel = FactoryOwnerUseCase.getRoleEmployeeModel();

        when(rolePersistentPort.getRoleByName(roleEmployeeName))
                .thenReturn(Optional.of(roleModel));
        when(userPersistentPort.getUserByEmail(employeeEmail))
                .thenReturn(Optional.of(userModel));

        Assertions.assertThrows(
                EmailAlreadyExistsException.class,
                () -> {
                    ownerUseCase.createEmployee(userModel);
                }
        );
    }
}
