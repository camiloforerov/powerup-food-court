package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CategoryWithDishesModel {
    private Long id;
    private String name;
    private String description;
    private List<DishModel> dishes;
}
