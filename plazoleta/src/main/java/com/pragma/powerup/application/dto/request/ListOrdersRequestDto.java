package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ListOrdersRequestDto {
    @Valid
    @Pattern(regexp = "^(PENDING|PREPARATION|CANCELED|READY|DELIVERED)$",
            message = "Only values allowed: PENDING, PREPARATION, CANCELED, READY or DELIVERED ")
    @NotBlank
    String orderState;
    @PositiveOrZero
    @NotNull
    @Valid
    int page;
    @Positive
    @Valid
    @NotNull
    int elementsPerPage;
}
