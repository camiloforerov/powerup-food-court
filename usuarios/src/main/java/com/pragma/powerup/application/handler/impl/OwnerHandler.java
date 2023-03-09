package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CreateEmployeeDto;
import com.pragma.powerup.application.dto.response.CreateEmployeeResponseDto;
import com.pragma.powerup.application.exception.exception.DataAlreadyExistsException;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IOwnerHandler;
import com.pragma.powerup.application.mapper.request.ICreateEmployeeRequestMapper;
import com.pragma.powerup.application.mapper.response.ICreateEmployeeResponseMapper;
import com.pragma.powerup.domain.api.IOwnerServicePort;
import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerHandler implements IOwnerHandler {
    private final IOwnerServicePort ownerServicePort;
    private final ICreateEmployeeResponseMapper createEmployeeResponseMapper;
    private final ICreateEmployeeRequestMapper createEmployeeRequestMapper;

    /**
     * Creates a restaurant employee
     *
     * @param createEmployeeDto - employee information
     * @throws EmailAlreadyExistsException - email already exists
     * @throws RoleNotFoundException role employee wasn't found
     * */
    @Override
    public CreateEmployeeResponseDto createEmployee(CreateEmployeeDto createEmployeeDto) {
        UserModel userModel = this.createEmployeeRequestMapper.toUserModel(createEmployeeDto);
        UserModel createdUserModel;
        try {
             createdUserModel = this.ownerServicePort.createEmployee(userModel);
        } catch (EmailAlreadyExistsException ex) {
            throw new DataAlreadyExistsException(ex.getMessage());
        } catch (RoleNotFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }

        return this.createEmployeeResponseMapper.toDto(createdUserModel);
    }
}
