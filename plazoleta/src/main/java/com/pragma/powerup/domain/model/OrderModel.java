package com.pragma.powerup.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class OrderModel {
    private Long id;
    private Date date;
    private String clientEmail;
    private String state;
    private String securityPin;
    private RestaurantModel restaurant;
    private RestaurantEmployeeModel chef;
}
