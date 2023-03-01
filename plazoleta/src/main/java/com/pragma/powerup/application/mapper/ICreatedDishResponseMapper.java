package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.CreatedDishResponseDto;
import com.pragma.powerup.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICreatedDishResponseMapper {
    @Mapping(target = "id", source = "dishModel.id")
    @Mapping(target = "name", source = "dishModel.name")
    @Mapping(target = "description", source = "dishModel.description")
    @Mapping(target = "price", source = "dishModel.price")
    @Mapping(target = "urlPicture", source = "dishModel.urlPicture")
    @Mapping(target = "categoryName", source = "dishModel.category.name")
    CreatedDishResponseDto toResponse(DishModel dishModel);
}
