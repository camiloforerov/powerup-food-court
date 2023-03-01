package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.powerup.application.handler.IAdminHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/food-court/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final IAdminHandler adminHandler;

    @Operation(summary = "Creates a new restaurant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Restaurant created", content = @Content),
        @ApiResponse(responseCode = "404",
                description = "User Id not corresponding to restaurant owner", content = @Content)
    })
    @PostMapping("/restaurant")
    public ResponseEntity<Void> createRestaurant(
            @RequestBody @Valid CreateRestaurantRequestDto createRestaurantRequestDto) {
        this.adminHandler.createRestaurant(createRestaurantRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
