package com.pragma.powerup.application.dto.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewOrderDishResponseDto {
    private Long id;
    private Date date;
    private String clientEmail;
    private String state;
    List<DishOrderResponseDto> dishes;
}
