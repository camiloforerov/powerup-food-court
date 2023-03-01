package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CreateEmployeeDto;
import com.pragma.powerup.application.dto.request.CreateRestaurantOwnerRequestDto;
import com.pragma.powerup.application.dto.response.CreateEmployeeResponseDto;
import com.pragma.powerup.application.handler.IOwnerHandler;
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
@RequestMapping("/users/v1/owner")
@RequiredArgsConstructor
public class OwnerRestController {
    private final IOwnerHandler ownerHandler;
    @Operation(summary = "Creates a new restaurant employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee resource related not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/employee")
    public ResponseEntity<CreateEmployeeResponseDto> createEmployee(@RequestBody @Valid CreateEmployeeDto createEmployeeDto) {
        return new ResponseEntity<>(this.ownerHandler.createEmployee(createEmployeeDto), HttpStatus.CREATED);
    }
}
