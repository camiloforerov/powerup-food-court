package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CreateDishRequestDto;
import com.pragma.powerup.application.dto.request.CreateEmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishStateRequestDto;
import com.pragma.powerup.application.dto.response.CreatedDishResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantEmployeeResponseDto;
import com.pragma.powerup.application.dto.response.UpdatedDishResponseDto;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IOwnerHandler;
import com.pragma.powerup.application.mapper.ICreateDishRequestMapper;
import com.pragma.powerup.application.mapper.ICreateEmployeeMapper;
import com.pragma.powerup.application.mapper.ICreatedDishResponseMapper;
import com.pragma.powerup.application.mapper.IUpdateDishMapper;
import com.pragma.powerup.domain.api.IOwnerServicePort;
import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.exceptions.NoRestaurantForOwnerFoundException;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerHandler implements IOwnerHandler {
    private final IOwnerServicePort ownerServicePort;
    private final ICreateDishRequestMapper createDishRequestMapper;
    private final ICreatedDishResponseMapper createdDishResponseMapper;
    private final ICreateEmployeeMapper createEmployeeRequestMapper;
    private final IUpdateDishMapper updateDishMapper;

    /**
     * Creates a new dish
     *
     * @param createDishDto - dish information and category name
     * @return dish created
     * */
    @Override
    public CreatedDishResponseDto createDish(CreateDishRequestDto createDishDto) {
        String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        RestaurantModel restaurantModel;
        try {
            restaurantModel = this.ownerServicePort.getRestaurantByOwnerEmail(ownerEmail);
        } catch (NoRestaurantForOwnerFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }

        DishModel createdDish = this.ownerServicePort.createDish(
                this.createDishRequestMapper.toModel(createDishDto),
                restaurantModel,
                createDishDto.getCategoryId());
        return this.createdDishResponseMapper.toResponse(createdDish);
    }

    /**
     * Updates a dish
     *
     * @param updateDishRequestDto - information to update with the dish id
     * */
    @Override
    public void updateDish(UpdateDishRequestDto updateDishRequestDto) {
        String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            this.ownerServicePort.updateDish(updateDishRequestDto.getDishId(),
                    updateDishRequestDto.getPrice(),
                    updateDishRequestDto.getDescription(),
                    ownerEmail);
        } catch (DishDoesNotExistException | NoRestaurantForOwnerFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
    }


    /**
     * Creates an employee, takes the restaurant of the authenticated owner to create
     * the employee relationship
     *
     * @param createEmployeeRequestDto - employee information
     * @throws NoDataFoundException - couldn't find the restaurant
     * @return employee email and related restaurant id
     * */
    @Override
    public RestaurantEmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) {
        String ownerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        RestaurantModel restaurantModel;
        try {
            restaurantModel = this.ownerServicePort.getRestaurantByOwnerEmail(ownerEmail);
        } catch (NoRestaurantForOwnerFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
        UserModel userModel = this.createEmployeeRequestMapper.toUserModel(createEmployeeRequestDto);
        RestaurantEmployeeModel restaurantEmployeeModel = this.ownerServicePort.createEmployee(userModel,
                createEmployeeRequestDto.getRoleId(),
                restaurantModel.getId()
        );
        return this.createEmployeeRequestMapper.toRestaurantEmployee(restaurantEmployeeModel);
    }

    /**
     * Updates dish state
     *
     * @param updateDishRequestDto - dish id and new state
     * */
    @Override
    public UpdatedDishResponseDto updateDishState(UpdateDishStateRequestDto updateDishRequestDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        DishModel dishModel;
        try {
            dishModel = this.ownerServicePort.updateDishState(
                    updateDishRequestDto.getDishId(),
                    updateDishRequestDto.getNewState(),
                    userEmail
            );
        } catch (DishDoesNotExistException | NoRestaurantForOwnerFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
        return this.updateDishMapper.toDto(dishModel);
    }
}
