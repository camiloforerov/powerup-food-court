package com.pragma.powerup.domain.usecase.user;

import com.pragma.powerup.domain.exception.UserNotFoundException;
import com.pragma.powerup.domain.factory.FactoryAdminUseCase;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GetUserUseCaseTest {
    @InjectMocks
    UserUseCase userUseCase;
    @Mock
    IUserPersistentPort userPersistentPort;
    @Test
    void mustGetUserByItsEmail() {
        String ownerEmail = "owner@gmail.com";
        UserModel userModel = FactoryAdminUseCase.getUserModel();

        when(userPersistentPort.getUserByEmail(ownerEmail))
                .thenReturn(Optional.of(userModel));

        Assertions.assertEquals(userModel, userUseCase.getUserByEmail(ownerEmail));
    }

    @Test
    void throwsUserDoesNotExistWhenAttemptFindUserByItsEmail() {
        String ownerEmail = "owner@gmail.com";

        when(userPersistentPort.getUserByEmail(ownerEmail))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                UserNotFoundException.class,
                () -> {
                    userUseCase.getUserByEmail(ownerEmail);
                }

        );
    }
}
