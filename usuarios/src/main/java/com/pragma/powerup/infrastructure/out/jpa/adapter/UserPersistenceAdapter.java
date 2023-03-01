package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RoleModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistentPort {
    private final IUserEntityMapper userEntityMapper;
    private final IUserRepository userRepository;
    private final IRoleEntityMapper roleEntityMapper;

    /**
     * Saves a user in the user's table on the database
     *
     * @param userModel - data of the user
     * */
    @Override
    public UserModel saveUser(UserModel userModel) {
        UserEntity savedUserEntity = this.userRepository.save(this.userEntityMapper.toEntity(userModel));
        return this.userEntityMapper.toUserModel(savedUserEntity);
    }

    /**
     * Search for a user by their email
     *
     * @param email - user's email
     * @return Optional of user information
     * */
    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        UserEntity userEntity = this.userRepository.findByEmail(email).orElse(null);
        return Optional.ofNullable(this.userEntityMapper.toUserModel(userEntity));
    }

    /**
     * Search for a user with id
     *
     * @param email - user email
     * @return restaurant owner in user model object
     * */
    @Override
    public Optional<UserModel> getUserByEmailAndRole(String email, RoleModel roleModel) {
        RoleEntity roleEntity = this.roleEntityMapper.toEntity(roleModel);
        UserEntity userEntity = this.userRepository.findByEmailAndRole(email, roleEntity).orElse(null);
        return Optional.ofNullable(this.userEntityMapper.toUserModel(userEntity));
    }
}
