package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CreateRestaurantRequestDto;
import com.pragma.powerup.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICreateRestaurantRequestMapper {
    @Mapping(target = "name", source = "createRestaurantRequestDto.name")
    @Mapping(target = "address", source = "createRestaurantRequestDto.address")
    @Mapping(target = "ownerEmail", source = "createRestaurantRequestDto.ownerEmail")
    @Mapping(target = "phone", source = "createRestaurantRequestDto.phone")
    @Mapping(target = "nit", source = "createRestaurantRequestDto.nit")
    @Mapping(target = "urlLogo", source = "createRestaurantRequestDto.urlLogo")
    RestaurantModel toModel(CreateRestaurantRequestDto createRestaurantRequestDto);
}
