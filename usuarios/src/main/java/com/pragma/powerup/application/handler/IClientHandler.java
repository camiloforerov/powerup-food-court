package com.pragma.powerup.application.handler;


import com.pragma.powerup.application.dto.request.CreateClientRequestDto;

public interface IClientHandler {
    void createClientAccount(CreateClientRequestDto createClientRequestDto);
}
