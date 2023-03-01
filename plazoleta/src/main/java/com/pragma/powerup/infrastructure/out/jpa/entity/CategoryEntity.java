package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CATEGORIA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "descripcion", nullable = true)
    private String description;

    @OneToMany(mappedBy = "category")
    private List<DishEntity> dish;
}
