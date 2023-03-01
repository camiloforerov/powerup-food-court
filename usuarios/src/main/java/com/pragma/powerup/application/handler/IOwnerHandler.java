package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.CreateEmployeeDto;
import com.pragma.powerup.application.dto.response.CreateEmployeeResponseDto;

public interface IOwnerHandler {
    CreateEmployeeResponseDto createEmployee(CreateEmployeeDto userModel);
}
