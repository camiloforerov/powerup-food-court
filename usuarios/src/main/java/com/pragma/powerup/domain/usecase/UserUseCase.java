package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.exception.UserNotFoundException;
import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {
    private final IUserPersistentPort userPersistentPort;
    private final IRolePersistentPort rolePersistentPort;

    /**
     * Gets a restaurant owner with its identification
     *
     * @param email - restaurant owner email
     * @return restaurant owner information
     * @throws RoleNotFoundException role owner not found on database
     * @throws UserNotFoundException restaurant owner not found by id
     * */
    @Override
    public UserModel getRestaurantOwnerByEmail(String email) {
        RoleModel roleModel = this.rolePersistentPort
                .getRoleByName(Constants.ROLE_OWNER_NAME).orElse(null);
        if (roleModel == null) {
            throw new RoleNotFoundException("Owner role not found");
        }
        UserModel userModel = this.userPersistentPort.getUserByEmailAndRole(email, roleModel).orElse(null);
        if (userModel == null) {
            throw new UserNotFoundException("Restaurant owner not found");
        }
        return userModel;
    }

    /**
     * Gets a user by email
     *
     * @param email - user's email
     * @return user model corresponding to the user, if not found, returns null
     * @throws UserNotFoundException user not found by email
     * */
    @Override
    public UserModel getUserByEmail(String email) {
        UserModel userModel = this.userPersistentPort.getUserByEmail(email).orElse(null);
        if (userModel == null) {
            throw new UserNotFoundException("User not found");
        }
        return userModel;
    }
}
