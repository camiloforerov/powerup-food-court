package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;
import com.pragma.powerup.domain.model.RestaurantModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IListRestaurantsClientMapper {
    @Mapping(target = "name", source = "restaurantModel.name")
    @Mapping(target = "urlLogo", source = "restaurantModel.urlLogo")
    RestaurantForClientResponseDto toDto(RestaurantModel restaurantModel);
}
