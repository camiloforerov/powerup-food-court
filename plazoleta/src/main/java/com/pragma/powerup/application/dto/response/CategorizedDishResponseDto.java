package com.pragma.powerup.application.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CategorizedDishResponseDto {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private List<GetDishResponseDto> dishes;
}
