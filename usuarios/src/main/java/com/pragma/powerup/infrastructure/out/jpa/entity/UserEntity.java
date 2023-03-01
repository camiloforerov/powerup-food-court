package com.pragma.powerup.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USUARIOS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "correo", nullable = false)
    private String email;

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "apellido", nullable = false)
    private String lastname;

    @Column(name = "celular", nullable = false, length = 13)
    private String phone;

    @Column(name = "clave", nullable = false)
    private String password;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rol", nullable = false, referencedColumnName = "id")
    private RoleEntity role;
}
