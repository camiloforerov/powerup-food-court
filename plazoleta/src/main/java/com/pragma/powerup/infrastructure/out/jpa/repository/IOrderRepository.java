package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderStateType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT p FROM OrderEntity p WHERE p.clientEmail=?1 AND p.state IN ?2")
    List<OrderEntity> findByClientEmailAndInProcessState(String clientEmail, List<OrderStateType> inProcessStates);

    List<OrderEntity> findByRestaurantIdAndState(Long restaurantId, OrderStateType orderStateType, Pageable pageable);

    Optional<OrderEntity> findByRestaurantIdAndId(Long restaurantId, Long orderId);

    List<OrderEntity> findBySecurityPinAndState(String securityPin, OrderStateType orderStateType);
}
