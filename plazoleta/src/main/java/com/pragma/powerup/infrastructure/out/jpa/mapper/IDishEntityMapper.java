package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.mapstruct.*;

@Named("dishEntityMapper")
@Mapper(componentModel = "spring",
        uses = IRestaurantEntityMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IDishEntityMapper {
    DishEntity toEntity(DishModel dishModel);

    @Named("toModel")
    @Mapping(source = "dishEntity.id", target = "id")
    @Mapping(source = "dishEntity.name", target = "name")
    @Mapping(source = "dishEntity.description", target = "description")
    @Mapping(source = "dishEntity.price", target = "price")
    @Mapping(source = "dishEntity.urlPicture", target = "urlPicture")
    @Mapping(source = "dishEntity.active", target = "active")
    @Mapping(target = "restaurant", qualifiedByName = {"restaurantEntityMapper", "toModelNoDishes"})
    DishModel toModel(DishEntity dishEntity);
}
