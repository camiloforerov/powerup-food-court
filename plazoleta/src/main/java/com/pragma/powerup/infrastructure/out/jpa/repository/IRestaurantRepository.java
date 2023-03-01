package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    List<RestaurantEntity> findByOwnerEmail(String ownerEmail);
}
