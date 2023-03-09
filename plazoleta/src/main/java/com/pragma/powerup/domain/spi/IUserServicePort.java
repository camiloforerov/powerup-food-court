package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.userservice.UserModel;

public interface IUserServicePort {
    boolean userIsRestaurantOwner(String email);
    UserModel createEmployee(UserModel userModel);
    UserModel getUserByEmail(String email);
}
