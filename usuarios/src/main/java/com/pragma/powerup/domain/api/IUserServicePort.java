package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

public interface IUserServicePort {
    UserModel getRestaurantOwnerByEmail(String email);
    UserModel getUserByEmail(String email);
}
