package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.powerup.application.handler.IAdminHandler;
import com.pragma.powerup.application.mapper.ICreateRestaurantRequestMapper;
import com.pragma.powerup.domain.api.IAdminServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminHandler implements IAdminHandler {
    private final IAdminServicePort adminServicePort;
    private final ICreateRestaurantRequestMapper createRestaurantRequestMapper;
    @Override
    public void createRestaurant(CreateRestaurantRequestDto createRestaurantRequestDto) {
        adminServicePort.createRestaurant(createRestaurantRequestMapper.toModel(createRestaurantRequestDto));
    }
}
