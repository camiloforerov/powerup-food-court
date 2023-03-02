package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.UpdateDishStateRequestDto;
import com.pragma.powerup.application.dto.response.UpdatedDishResponseDto;
import com.pragma.powerup.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUpdateDishMapper {
    @Mapping(target = "id", source = "dishModel.id")
    @Mapping(target = "name", source = "dishModel.name")
    @Mapping(target = "description", source = "dishModel.description")
    @Mapping(target = "price", source = "dishModel.price")
    @Mapping(target = "urlPicture", source = "dishModel.urlPicture")
    @Mapping(target = "active", source = "dishModel.active")
    UpdatedDishResponseDto toDto(DishModel dishModel);
}
