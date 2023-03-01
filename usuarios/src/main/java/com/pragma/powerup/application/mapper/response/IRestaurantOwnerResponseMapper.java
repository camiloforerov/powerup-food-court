package com.pragma.powerup.application.mapper.response;

import com.pragma.powerup.application.dto.response.RestaurantOwnerResponseDto;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantOwnerResponseMapper {
    @Mapping(source = "userModel.id", target = "id")
    @Mapping(source = "userModel.name", target = "name")
    @Mapping(source = "userModel.lastname", target = "lastname")
    @Mapping(source = "userModel.phone", target = "phone")
    @Mapping(source = "userModel.email", target = "email")
    RestaurantOwnerResponseDto toResponseDto(UserModel userModel);
}
