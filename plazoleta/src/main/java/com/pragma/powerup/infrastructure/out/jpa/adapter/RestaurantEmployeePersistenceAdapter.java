package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantEmployeePersistenceAdapter implements IRestaurantEmployeePersistentPort {
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    @Override
    public RestaurantEmployeeModel save(RestaurantEmployeeModel restaurantEmployeeModel) {
        RestaurantEmployeeEntity restaurantEmployeeEntity = this.restaurantEmployeeEntityMapper
                .toEntity(restaurantEmployeeModel);
        return this.restaurantEmployeeEntityMapper.toModel(
                this.restaurantEmployeeRepository.save(restaurantEmployeeEntity)
        );
    }

    @Override
    public Optional<RestaurantEmployeeModel> findByEmployeeEmail(String email) {
        RestaurantEmployeeEntity restaurantEmployeeEntity = this.restaurantEmployeeRepository
                .findById(email).orElse(null);
        return Optional.ofNullable(this.restaurantEmployeeEntityMapper.toModel(restaurantEmployeeEntity));
    }
}
