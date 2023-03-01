package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PEDIDOS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha", nullable = false)
    private Date date;

    @Column(name = "estado", nullable = false)
    private OrderStateType state;

    @Column(name = "email_cliente", nullable = false)
    private String clientEmail;

    @OneToMany(mappedBy = "order")
    private List<OrderDishEntity> orderDishes;

    @ManyToOne
    @JoinColumn(name = "email_chef", referencedColumnName = "email_persona")
    private RestaurantEmployeeEntity restaurantEmployee;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_resturante", referencedColumnName = "id")
    private RestaurantEntity restaurant;


}
