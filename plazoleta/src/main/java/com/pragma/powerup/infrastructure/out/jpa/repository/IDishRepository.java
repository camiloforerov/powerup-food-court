package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    List<DishEntity> findByRestaurantId(Long restaurantId);
    List<DishEntity> findByRestaurantIdAndActive(Long restaurantId, boolean active, Pageable pageable);
    Optional<DishEntity> findByIdAndRestaurantId(Long dishId, Long restaurantId);
}
