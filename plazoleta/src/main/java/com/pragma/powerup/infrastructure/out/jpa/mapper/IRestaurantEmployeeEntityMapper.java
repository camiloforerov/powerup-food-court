package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Named("restaurantEmployeeEntityMapper")
@Mapper(componentModel = "spring",
        uses = IRestaurantEntityMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRestaurantEmployeeEntityMapper {
    RestaurantEmployeeEntity toEntity(RestaurantEmployeeModel restaurantEmployeeModel);

    @Named("toModel")
    @Mapping(source = "restaurantEmployeeEntity.userEmail", target = "userEmail")
    @Mapping(target = "restaurant", qualifiedByName = {"restaurantEntityMapper", "toModelNoDishes"})
    RestaurantEmployeeModel toModel(RestaurantEmployeeEntity restaurantEmployeeEntity);
}
