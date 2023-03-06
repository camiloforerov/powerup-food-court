package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.CategorizedDishResponseDto;
import com.pragma.powerup.domain.model.CategoryWithDishesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IListDishesPerRestaurantMapper {
    @Mapping(target = "categoryId", source = "categoryWithDishesModel.id")
    @Mapping(target = "categoryName", source = "categoryWithDishesModel.name")
    @Mapping(target = "categoryDescription", source = "categoryWithDishesModel.description")
    @Mapping(target = "dishes", source = "categoryWithDishesModel.dishes")
    CategorizedDishResponseDto toDto(CategoryWithDishesModel categoryWithDishesModel);
}
