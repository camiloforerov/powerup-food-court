package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.DishOrderResponseDto;
import com.pragma.powerup.domain.model.OrderDishModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface INewOrderResponseMapper {
    @Mapping(target = "dishName", source = "orderDishModel.dishModel.name")
    @Mapping(target = "amount", source = "orderDishModel.amount")
    DishOrderResponseDto toDto(OrderDishModel orderDishModel);
}
