package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;

import java.util.Optional;

public interface IUserPersistentPort {
    UserModel saveUser(UserModel userModel);
    Optional<UserModel> getUserByEmail(String email);
    Optional<UserModel> getUserByEmailAndRole(String email, RoleModel roleModel);
}
