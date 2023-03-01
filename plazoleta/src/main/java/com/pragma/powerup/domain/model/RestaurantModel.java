package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantModel {
    private Long id;
    private String name;
    private String address;
    private String ownerEmail;
    private String phone;
    private String urlLogo;
    private Long nit;
    private List<DishModel> dishes;
}
