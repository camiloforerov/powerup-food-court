package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, OrderDishEntityKey> {
}
