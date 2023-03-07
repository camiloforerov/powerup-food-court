package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PEDIDOS_PLATOS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDishEntity {
    @EmbeddedId
    OrderDishEntityKey id = new OrderDishEntityKey();

    @ManyToOne(optional = false)
    @MapsId("orderId")
    @JoinColumn(name = "id_pedido", nullable = false)
    private OrderEntity order;

    @ManyToOne(optional = false)
    @MapsId("dishId")
    @JoinColumn(name = "id_plato", nullable = false)
    private DishEntity dish;

    @Column(name = "cantidad", nullable = false)
    private int amount;
}
