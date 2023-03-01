package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ROLES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "descripcion", nullable = false)
    private String description;

    @OneToMany(mappedBy = "role")
    private List<UserEntity> users;
}
