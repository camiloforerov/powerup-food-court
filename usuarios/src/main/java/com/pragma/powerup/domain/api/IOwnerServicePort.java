package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

public interface IOwnerServicePort {
    UserModel createEmployee(UserModel userModel);
}
