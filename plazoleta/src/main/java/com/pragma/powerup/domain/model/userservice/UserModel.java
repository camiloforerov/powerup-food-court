package com.pragma.powerup.domain.model.userservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private String password;
    private String role;
}
