package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishModel {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String urlPicture;
    private boolean active;
    private RestaurantModel restaurant;
    private CategoryModel category;
}
