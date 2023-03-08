package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.ListOrdersRequestDto;
import com.pragma.powerup.application.dto.response.AssignOrderResponseDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.application.handler.IEmployeeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/food-court/v1/employee")
@RequiredArgsConstructor
public class EmployeeRestController {
    private final IEmployeeHandler employeeHandler;

    @Operation(summary = "Get orders filtered by state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders", content = @Content),
            @ApiResponse(responseCode = "500", description = "Employee doesn't belong to any restaurant", content = @Content),
    })
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> listRestaurants(
            @Valid @RequestBody ListOrdersRequestDto listOrdersRequestDto
    ) {

        return ResponseEntity.ok(this.employeeHandler.listOrdersByState(listOrdersRequestDto.getOrderState(),
                listOrdersRequestDto.getPage(), listOrdersRequestDto.getElementsPerPage()));
    }

    @Operation(summary = "Assign multiple orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of orders", content = @Content),
            @ApiResponse(responseCode = "500", description = "Employee doesn't belong to any restaurant", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Order is already assigned", content = @Content)
    })
    @PutMapping("/assign-orders")
    public ResponseEntity<List<AssignOrderResponseDto>> assignOrders(@RequestBody @Valid List<Long> orderIds) {
        return ResponseEntity.ok(this.employeeHandler.assignOrdersToEmployee(orderIds));
    }
}
