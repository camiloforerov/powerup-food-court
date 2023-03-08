package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderWithDishesModel {
    private Long id;
    private Date date;
    private String clientEmail;
    private String state;
    private String chefEmail;
    private List<OrderDishModel> orderDishes;

}
