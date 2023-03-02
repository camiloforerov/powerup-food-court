package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CreateClientRequestDto;
import com.pragma.powerup.application.exception.exception.DataAlreadyExistsException;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IClientHandler;
import com.pragma.powerup.application.mapper.request.ICreateClientRequestMapper;
import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.exception.EmailAlreadyExistsException;
import com.pragma.powerup.domain.exception.RoleNotFoundException;
import com.pragma.powerup.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ClientHandler implements IClientHandler {
    private final IClientServicePort clientServicePort;
    private final ICreateClientRequestMapper createClientRequestMapper;

    /**
     * Creates a client
     *
     * @param createClientRequestDto - client information
     * @throws EmailAlreadyExistsException - client email is already taken by another user
     * @throws RoleNotFoundException - Role client not found on the database
     * */
    @Override
    public void createClientAccount(CreateClientRequestDto createClientRequestDto) {
        UserModel userModel = this.createClientRequestMapper.toModel(createClientRequestDto);
        try {
            this.clientServicePort.createClientAccount(userModel);
        } catch (EmailAlreadyExistsException ex) {
            throw new DataAlreadyExistsException(ex.getMessage());
        } catch (RoleNotFoundException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
    }
}
