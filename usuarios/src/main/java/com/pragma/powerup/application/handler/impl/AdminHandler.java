package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CreateRestaurantOwnerRequestDto;
import com.pragma.powerup.application.exception.exception.DataAlreadyExistsException;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IAdminHandler;
import com.pragma.powerup.application.mapper.request.ICreateRestaurantOwnerRequestMapper;
import com.pragma.powerup.domain.api.IAdminServicePort;
import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminHandler implements IAdminHandler {
    private final IAdminServicePort adminServicePort;
    private final ICreateRestaurantOwnerRequestMapper createRestaurantOwnerRequestMapper;

    /**
     * Creates the restaurant owner
     *
     * @param createRestaurantOwnerRequestDto - restaurant owner information
     * @throws EmailAlreadyExistsException - Owner email is already taken by another user
     * @throws RoleNotFoundException - Role not found on the database
     * */
    @Override
    public void createRestaurantOwner(CreateRestaurantOwnerRequestDto createRestaurantOwnerRequestDto) {
        UserModel userModel = this.createRestaurantOwnerRequestMapper.toUser(createRestaurantOwnerRequestDto);
        try {
            this.adminServicePort.createRestaurantOwner(userModel);
        } catch (EmailAlreadyExistsException ex) {
            throw new DataAlreadyExistsException(ex.getMessage());
        } catch (RoleNotFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
    }
}
