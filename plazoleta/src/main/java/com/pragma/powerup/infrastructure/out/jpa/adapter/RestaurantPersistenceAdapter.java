package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements IRestaurantPersistentPort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        this.restaurantRepository.save(this.restaurantEntityMapper.toEntity(restaurantModel));
    }

    @Override
    public RestaurantModel getRestaurantByOwnerEmail(String ownerEmail) {
        List<RestaurantEntity> restaurants = this.restaurantRepository.findByOwnerEmail(ownerEmail);
        return !restaurants.isEmpty() ? this.restaurantEntityMapper.toModelNoDishes(restaurants.get(0)) : null;
    }

    @Override
    public Optional<RestaurantModel> getRestaurantById(Long restaurantId) {
        RestaurantEntity restaurantEntity = this.restaurantRepository.findById(restaurantId).orElse(null);
        return Optional.ofNullable(this.restaurantEntityMapper.toModelNoDishes(restaurantEntity));
    }
}
