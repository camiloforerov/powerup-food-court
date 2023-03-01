package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = IRestaurantEntityMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEmployeeEntityMapper {
    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel restaurantEmployeeModel);

    @Mapping(source = "restaurantEmployeeEntity.userEmail", target = "userEmail")
    @Mapping(target = "restaurant", qualifiedByName = {"restaurantEntityMapper", "toModelNoDishes"})
    RestaurantEmployeeModel toModel(RestaurantEmployeeEntity restaurantEmployeeEntity);
}
