package com.pragma.powerup.infrastructure.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "RESTAURANTES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestaurantEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "direccion", nullable = false)
    private String address;

    @Column(name = "email_propietario", nullable = false)
    private String ownerEmail;

    @Column(name = "telefono", nullable = false)
    private String phone;

    @Column(name = "urlLogo", nullable = true)
    private String urlLogo;

    @Column(name = "nit", nullable = false)
    private Long nit;

    @OneToMany(mappedBy = "restaurant")
    private List<DishEntity> dishes;

    @OneToOne(mappedBy = "restaurant")
    private OrderEntity order;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantEmployeeEntity> restaurantEmployees;
}
