package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ListOrdersRequestDto {
    @Pattern(regexp = "^(PENDING|PREPARATION|CANCELED|READY|DELIVERED)$",
            message = "Only values allowed: PENDING, PREPARATION, CANCELED, READY or DELIVERED ")
    @NotBlank(message = "Order state cannot be null")
    String orderState;
    @PositiveOrZero(message = "Page number must be positive or zero")
    @NotNull(message = "Page number cannot be null")
    int page;
    @Positive(message = "Elements per page must be positive")
    @NotNull(message = "Elements per page cannot be null")
    int elementsPerPage;
}
