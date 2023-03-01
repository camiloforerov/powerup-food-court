package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class CreateDishRequestDto {
    @NotBlank(message = "Name cannot be empty")
    @Valid
    private String name;
    @NotNull(message = "Price cannot be blank")
    @PositiveOrZero(message = "Price cannot be negative")
    @Valid
    private Double price;
    @NotBlank(message = "Description cannot be blank")
    @Valid
    private String description;
    @NotBlank(message = "Url picture is mandatory")
    @Valid
    private String urlPicture;
    @NotNull(message = "Category id cannot be empty")
    @Valid
    private Long categoryId;
}
