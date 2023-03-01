package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CreateEmployeeRequestDto;
import com.pragma.powerup.application.dto.response.RestaurantEmployeeResponseDto;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICreateEmployeeMapper {
    @Mapping(source = "createEmployeeRequestDto.id", target = "id")
    @Mapping(source = "createEmployeeRequestDto.name", target = "name")
    @Mapping(source = "createEmployeeRequestDto.lastname", target = "lastname")
    @Mapping(source = "createEmployeeRequestDto.phone", target = "phone")
    @Mapping(source = "createEmployeeRequestDto.email", target = "email")
    @Mapping(source = "createEmployeeRequestDto.password", target = "password")
    UserModel toUserModel(CreateEmployeeRequestDto createEmployeeRequestDto);

    @Mapping(source = "restaurantEmployeeModel.userEmail", target = "email")
    @Mapping(source = "restaurantEmployeeModel.restaurant.id", target = "restaurantId")
    RestaurantEmployeeResponseDto toRestaurantEmployee(RestaurantEmployeeModel restaurantEmployeeModel);
}
