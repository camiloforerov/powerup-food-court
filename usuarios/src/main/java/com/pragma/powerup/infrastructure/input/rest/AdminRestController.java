package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CreateRestaurantOwnerRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantOwnerResponseDto;
import com.pragma.powerup.application.handler.IAdminHandler;
import com.pragma.powerup.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/v1/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final IAdminHandler adminHandler;
    private final IUserHandler userHandler;

    @Operation(summary = "Creates a new restaurant owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Owner resource related not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Owner already exists", content = @Content)
    })
    @PostMapping("/create-owner")
    public ResponseEntity<Void> createOwner(@RequestBody @Valid CreateRestaurantOwnerRequestDto createRestaurantOwnerRequestDto) {
        this.adminHandler.createRestaurantOwner(createRestaurantOwnerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Search for a restaurant owner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant owner found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Restaurant owner not found", content = @Content)
    })
    @GetMapping("/restaurant-owner")
    public ResponseEntity<RestaurantOwnerResponseDto> getOwnerUser(@RequestParam("email") String email) {
        return new ResponseEntity<>(this.userHandler.getRestaurantOwner(email), HttpStatus.OK);
    }
}
