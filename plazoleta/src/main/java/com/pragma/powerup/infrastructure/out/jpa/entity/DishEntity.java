package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PLATOS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @Column(name = "precio", nullable = false)
    private double price;

    @Column(name = "url_imagen", nullable = true)
    private String urlPicture;

    @Column(name = "activo", nullable = false)
    private boolean active;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_restaurante", nullable = false)
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "dish")
    private List<OrderDishEntity> orderDishes;
}
