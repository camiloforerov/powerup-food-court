package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RESTAURANTE_EMPLEADO")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEmployeeEntity {
    @Id
    @Column(name = "email_persona", nullable = false)
    private String userEmail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_restaurante", nullable = false)
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "restaurantEmployee")
    private List<OrderEntity> order;
}
