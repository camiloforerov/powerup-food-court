package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.out.feign.dto.request.CreateEmployeeRequestDto;
import com.pragma.powerup.infrastructure.out.feign.dto.response.CreateEmployeeResponseDto;
import com.pragma.powerup.infrastructure.out.feign.dto.response.RestaurantOwnerResponseDto;
import com.pragma.powerup.infrastructure.out.feign.dto.response.UserResponseDto;
import com.pragma.powerup.infrastructure.out.feign.exceptions.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service",
        url = "http://localhost:8082/users/v1",
        configuration = CustomErrorDecoder.class,
        decode404 = true
)
public interface UserServiceFeignClient {
    @GetMapping(value = "/auth/user", produces = "application/json")
    UserResponseDto getUserByEmail(@RequestParam("email") String email);

    @GetMapping(value = "/admin/restaurant-owner", produces = "application/json")
    RestaurantOwnerResponseDto getRestaurantOwnerByEmail(@RequestParam("email") String email);

    @PostMapping(value = "/owner/employee")
    CreateEmployeeResponseDto createEmployee(@RequestBody CreateEmployeeRequestDto createEmployeeRequestDto);
}
