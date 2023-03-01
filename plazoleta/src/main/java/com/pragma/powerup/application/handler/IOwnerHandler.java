package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CreateDishRequestDto;
import com.pragma.powerup.application.dto.request.CreateEmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.dto.response.CreatedDishResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantEmployeeResponseDto;

public interface IOwnerHandler {
    CreatedDishResponseDto createDish(CreateDishRequestDto createDishDto);
    void updateDish(UpdateDishRequestDto updateDishRequestDto);
    RestaurantEmployeeResponseDto createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto);
}
