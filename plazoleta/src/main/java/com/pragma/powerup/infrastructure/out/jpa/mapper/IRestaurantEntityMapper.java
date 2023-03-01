package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import org.mapstruct.*;
@Named("restaurantEntityMapper")
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(RestaurantModel restaurantModel);
    RestaurantModel toModel(RestaurantEntity restaurantEntity);

    @Named("toModelNoDishes")
    @Mapping(target = "dishes", ignore = true)
    RestaurantModel toModelNoDishes(RestaurantEntity restaurantEntity);
}
