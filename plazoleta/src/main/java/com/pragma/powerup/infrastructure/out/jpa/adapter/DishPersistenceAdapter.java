package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<DishModel> getDishByIdAndRestaurantId(Long dishId, Long restaurantId) {
        DishEntity dishEntity = this.dishRepository.findByIdAndRestaurantId(dishId, restaurantId).orElse(null);
        return Optional.ofNullable(this.dishEntityMapper.toModel(dishEntity));
    }

    @Override
    public List<DishModel> getDishesByRestaurantId(Long restaurantId) {
        List<DishEntity> dishesEntity = this.dishRepository.findByRestaurantId(restaurantId);
        return dishesEntity.stream()
                .map(dishE -> this.dishEntityMapper.toModel(dishE))
                .collect(Collectors.toList());
    }

    @Override
    public List<DishModel> listDishesByRestaurantPageable(
            Long restaurantId,
            int pageNumber,
            int elementsPerPage
    ) {
        PageRequest pageable = PageRequest.of(
                pageNumber,
                elementsPerPage
        );
        List<DishEntity> dishEntities = this.dishRepository
                .findByRestaurantIdAndActive(restaurantId, true, pageable);
        return dishEntities.stream()
                .map(dishE -> this.dishEntityMapper.toModel(dishE))
                .collect(Collectors.toList());
    }
}
