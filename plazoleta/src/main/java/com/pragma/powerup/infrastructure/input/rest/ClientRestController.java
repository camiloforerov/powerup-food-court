package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.CategorizedDishResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;
import com.pragma.powerup.application.handler.IClientHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/food-court/v1/client")
@RequiredArgsConstructor
public class ClientRestController {
    private final IClientHandler clientHandler;

    @Operation(summary = "Get all the restaurants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurants list returned", content = @Content),
    })
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantForClientResponseDto>> listRestaurants(
            @PositiveOrZero @RequestParam("pageNumber") int page,
            @Positive @RequestParam("elementsPerPage") int elementsPerPage
    ) {
        return ResponseEntity.ok(this.clientHandler.listRestaurants(page, elementsPerPage));
    }

    @Operation(summary = "Find all dishes for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of dishes categorized returned", content = @Content),
    })
    @GetMapping("/dishes-restaurant")
    public ResponseEntity<List<CategorizedDishResponseDto>> getDishesListPerRestaurant(
            @RequestParam("restaurantId") Long restaurantId,
            @PositiveOrZero @RequestParam("pageNumber") int page,
            @Positive @RequestParam("elementsPerPage") int elementsPerPage
    ) {
        return new ResponseEntity<>(this.clientHandler.
                listRestaurantCategorizedDishes(restaurantId, page, elementsPerPage), HttpStatus.OK);
    }
}
