package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CreateDishRequestDto;
import com.pragma.powerup.domain.model.DishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICreateDishRequestMapper {
    @Mapping(target = "name", source = "createDishRequestDto.name")
    @Mapping(target = "price", source = "createDishRequestDto.price")
    @Mapping(target = "description", source = "createDishRequestDto.description")
    @Mapping(target = "urlPicture", source = "createDishRequestDto.urlPicture")
    DishModel toModel(CreateDishRequestDto createDishRequestDto);
}
