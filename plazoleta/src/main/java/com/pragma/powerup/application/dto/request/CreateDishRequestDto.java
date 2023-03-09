package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
public class CreateDishRequestDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 150, message = "Name must be between 1 and 150 characters")
    private String name;
    @NotNull(message = "Price cannot be blank")
    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;
    @NotBlank(message = "Description cannot be blank")
    @Size(min = 1, max = 255, message = "Description must be between 1 and 150 characters")
    private String description;
    @NotBlank(message = "Url picture is mandatory")
    @Size(min = 1, max = 255, message = "urlPicture must be between 1 and 150 characters")
    private String urlPicture;
    @NotNull(message = "Category id cannot be empty")
    private Long categoryId;
}
