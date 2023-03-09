package com.pragma.powerup.infrastructure.out.feign.adapter;

import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IUserServicePort;
import com.pragma.powerup.infrastructure.out.feign.UserServiceFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.request.CreateEmployeeRequestDto;
import com.pragma.powerup.infrastructure.out.feign.dto.response.RestaurantOwnerResponseDto;
import com.pragma.powerup.infrastructure.out.feign.dto.response.UserResponseDto;
import com.pragma.powerup.infrastructure.out.feign.mapper.ICreateEmployeeRequestMapper;
import com.pragma.powerup.infrastructure.out.feign.mapper.ICreateEmployeeResponseMapper;
import com.pragma.powerup.infrastructure.out.feign.mapper.IUserResponseMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceAdapter implements IUserServicePort {
    private final UserServiceFeignClient userServiceFeignClient;
    private final ICreateEmployeeRequestMapper createEmployeeRequestMapper;
    private final ICreateEmployeeResponseMapper createEmployeeResponseMapper;
    private final IUserResponseMapper userResponseMapper;

    /**
     * Calls user service to verify if the userId correspond to a restaurant owner
     *
     * @param email - user email
     * @return true if it's a restaurant owner, otherwise, false
     * */
    @Override
    public boolean userIsRestaurantOwner(String email) {
        RestaurantOwnerResponseDto restaurantOwnerResponseDto =
                this.userServiceFeignClient.getRestaurantOwnerByEmail(email);

        return restaurantOwnerResponseDto != null ? true : false;
    }

    /**
     * Calls user service to get the user by email
     *
     * @param email - user's email
     * @return User model of the corresponding user
     * */
    @Override
    public UserModel getUserByEmail(String email) {
        UserResponseDto userResponseDto = this.userServiceFeignClient.getUserByEmail(email);
        return this.userResponseMapper.toModel(userResponseDto);
    }

    /**
     * Calls user service to create employee's user entity
     *
     * @param userModel - employee information
     * */
    @Override
    public UserModel createEmployee(UserModel userModel) {
        CreateEmployeeRequestDto createEmployeeRequestDto = this.createEmployeeRequestMapper.toDto(userModel);
        return this.createEmployeeResponseMapper.toModel(this.userServiceFeignClient.createEmployee(createEmployeeRequestDto));
    }
}
