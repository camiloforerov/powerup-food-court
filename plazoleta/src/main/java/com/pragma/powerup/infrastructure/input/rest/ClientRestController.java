package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;
import com.pragma.powerup.application.handler.IClientHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food-court/v1/client")
@RequiredArgsConstructor
public class ClientRestController {
    private final IClientHandler clientHandler;

    @Operation(summary = "Get all the restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant created", content = @Content),
    })
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantForClientResponseDto>> createRestaurant(
            @RequestParam("pageNumber") int page,
            @RequestParam("elementsPerPage") int elementsPerPage
    ) {
        return ResponseEntity.ok(this.clientHandler.listRestaurants(page, elementsPerPage));
    }
}
