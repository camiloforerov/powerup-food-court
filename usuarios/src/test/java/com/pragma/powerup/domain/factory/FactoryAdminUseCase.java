package com.pragma.powerup.domain.factory;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;

public class FactoryAdminUseCase {
    public static UserModel getUserModel() {
        UserModel userModel = new UserModel();
        userModel.setName("John");
        userModel.setLastname("Doe");
        userModel.setId(1L);
        userModel.setPhone("3232");
        userModel.setEmail("juan@gmail.com");
        userModel.setPassword("4321");
        return userModel;
    }

    public static RoleModel getRoleModel() {
        RoleModel roleModel = new RoleModel();
        roleModel.setId(1L);
        roleModel.setName("ROLE_OWNER");
        roleModel.setDescription("Restaurant Owner");
        return roleModel;
    }

}
