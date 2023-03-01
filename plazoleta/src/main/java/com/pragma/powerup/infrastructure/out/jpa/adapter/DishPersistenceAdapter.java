package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishPersistenceAdapter implements IDishPersistentPort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

    @Override
    public DishModel saveDish(DishModel dishModel) {
        DishEntity dishEntityResult = this.dishRepository.save(dishEntityMapper.toEntity(dishModel));
        return this.dishEntityMapper.toModel(dishEntityResult);
    }

    @Override
    public DishModel getDishById(Long dishId) {
        DishEntity dishEntity = this.dishRepository.findById(dishId).orElse(null);
        return this.dishEntityMapper.toModel(dishEntity);
    }
}
