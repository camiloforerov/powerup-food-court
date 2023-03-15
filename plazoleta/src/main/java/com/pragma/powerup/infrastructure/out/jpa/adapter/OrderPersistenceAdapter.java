package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderStateType;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderPersistenceAdapter implements IOrderPersistentPort {
    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public List<OrderModel> getInProcessOrdersByClientEmail(String email) {
        List<OrderStateType> inProcessStates = new ArrayList<>();
        inProcessStates.add(OrderStateType.EN_PREPARACION);
        inProcessStates.add(OrderStateType.LISTO);
        inProcessStates.add(OrderStateType.PENDIENTE);
        List<OrderEntity> ordersEntity = orderRepository.findByClientEmailAndInProcessState(email, inProcessStates);
        return ordersEntity.stream()
                .map(orderEntity -> this.orderEntityMapper.toModel(orderEntity))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDishModel saveOrderDish(OrderDishModel orderDishModel) {
        OrderDishEntity orderDishEntity = this.orderDishEntityMapper.toEntity(orderDishModel);
        OrderDishEntity savedOrderDishEntity = this.orderDishRepository.save(orderDishEntity);
        return this.orderDishEntityMapper.toModel(savedOrderDishEntity);
    }

    @Override
    public OrderModel saveOrder(OrderModel orderModel) {
        OrderEntity orderEntity = this.orderEntityMapper.toEntity(orderModel);
        return this.orderEntityMapper.toModel(this.orderRepository.save(orderEntity));
    }

    @Override
    public OrderWithDishesModel saveOrderToOrderWithDishes(OrderModel orderModel) {
        OrderEntity orderEntity = this.orderEntityMapper.toEntity(orderModel);
        return this.orderEntityMapper.toOrderWithDishesModel(this.orderRepository.save(orderEntity));
    }

    @Override
    public List<OrderWithDishesModel> getOrdersByRestaurantIdAndState(Long restaurantId,
                                                                      int page,
                                                                      int elementsPerPage,
                                                                      String state
    ) {
        OrderStateType orderStateType = this.convertStringToOrderStateType(state);
        PageRequest pageable = PageRequest.of(
                page,
                elementsPerPage
        );
        List<OrderEntity> ordersEntity = this.orderRepository.findByRestaurantIdAndState(restaurantId,
                orderStateType, pageable);
        return ordersEntity.stream()
                .map(orderEntityMapper::toOrderWithDishesModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderModel> getOrderByRestaurantIdAndOrderId(Long restaurantId, Long orderId) {
        OrderEntity orderEntity = this.orderRepository.findByRestaurantIdAndId(restaurantId, orderId).orElse(null);
        return Optional.ofNullable(this.orderEntityMapper.toModel(orderEntity));
    }

    @Override
    public List<OrderModel> getOrdersReadyBySecurityCode(String securityPin) {
        List<OrderEntity> ordersEntity = this.orderRepository.findBySecurityPinAndState(securityPin,
                OrderStateType.LISTO);
        return ordersEntity.stream()
                .map(this.orderEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderModel> getOrderById(Long orderId) {
        OrderEntity orderEntity = this.orderRepository.findById(orderId).orElse(null);
        return Optional.ofNullable(this.orderEntityMapper.toModel(orderEntity));
    }

    private OrderStateType convertStringToOrderStateType(String state) {
        OrderStateType stateToProcess;
        switch (state) {
            case Constants.ORDER_PENDING_STATE:
                stateToProcess = OrderStateType.PENDIENTE;
                break;
            case Constants.ORDER_CANCELED_STATE:
                stateToProcess = OrderStateType.CANCELADO;
                break;
            case Constants.ORDER_DELIVERED_STATE:
                stateToProcess = OrderStateType.ENTREGADO;
                break;
            case Constants.ORDER_READY_STATE:
                stateToProcess = OrderStateType.LISTO;
                break;
            default:
                stateToProcess = OrderStateType.EN_PREPARACION;
                break;
        }
        return stateToProcess;
    }
}
