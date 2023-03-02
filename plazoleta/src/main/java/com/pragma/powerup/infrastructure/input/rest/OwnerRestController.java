package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CreateDishRequestDto;
import com.pragma.powerup.application.dto.request.CreateEmployeeRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishRequestDto;
import com.pragma.powerup.application.dto.request.UpdateDishStateRequestDto;
import com.pragma.powerup.application.dto.response.CreatedDishResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantEmployeeResponseDto;
import com.pragma.powerup.application.dto.response.UpdatedDishResponseDto;
import com.pragma.powerup.application.handler.IOwnerHandler;
import com.pragma.powerup.infrastructure.out.feign.dto.response.CreateEmployeeResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/food-court/v1/owner")
@RequiredArgsConstructor
public class OwnerRestController {
    private final IOwnerHandler ownerHandler;

    @Operation(summary = "Creates a new dish")
    @ApiResponses(
            @ApiResponse(responseCode = "201", description = "Dish created", content = @Content)
    )
    @PostMapping("/dish")
    public ResponseEntity<CreatedDishResponseDto> createDish(
            @RequestBody @Valid CreateDishRequestDto createDishRequestDto) {
        return new ResponseEntity<>(this.ownerHandler.createDish(createDishRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Updates a dish information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Modification successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish doesn't exists", content = @Content)
    })
    @PutMapping("/dish")
    public ResponseEntity<Void> updateDish(@RequestBody @Valid UpdateDishRequestDto updateDishRequestDto) {
        this.ownerHandler.updateDish(updateDishRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Creates a new employee")
    @ApiResponses(
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content)
    )
    @PostMapping("/employee")
    public ResponseEntity<RestaurantEmployeeResponseDto> createEmployee(
            @RequestBody @Valid CreateEmployeeRequestDto createEmployeeRequestDto) {
        return new ResponseEntity<>(this.ownerHandler.createEmployee(createEmployeeRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Change state of a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State changed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish couldn't be found", content = @Content)
    })
    @PutMapping("/dish-state")
    public ResponseEntity<UpdatedDishResponseDto> changeDishState(
            @RequestBody @Valid UpdateDishStateRequestDto updateDishStateRequestDto) {
        return new ResponseEntity<>(this.ownerHandler.updateDishState(updateDishStateRequestDto), HttpStatus.OK);
    }
}
