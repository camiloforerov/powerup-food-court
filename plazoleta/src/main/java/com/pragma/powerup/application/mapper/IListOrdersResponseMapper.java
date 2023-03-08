package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.DishResponseDto;
import com.pragma.powerup.application.dto.response.OrderResponseDto;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IListOrdersResponseMapper {
    @Mapping(target = "id", source = "orderWithDishesModel.id")
    @Mapping(target = "date", source = "orderWithDishesModel.date")
    @Mapping(target = "clientEmail", source = "orderWithDishesModel.clientEmail")
    @Mapping(target = "state", source = "orderWithDishesModel.state")
    @Mapping(target = "chefEmail", source = "orderWithDishesModel.chefEmail")
    @Mapping(target = "orderDishes", source = "orderWithDishesModel.orderDishes", qualifiedByName = { "toDishResponseDto" })
    OrderResponseDto toDto(OrderWithDishesModel orderWithDishesModel);

    @Named("toDishResponseDto")
    @Mapping(target = "id", source = "orderDishModel.dishModel.id")
    @Mapping(target = "name", source = "orderDishModel.dishModel.name")
    @Mapping(target = "description", source = "orderDishModel.dishModel.description")
    @Mapping(target = "price", source = "orderDishModel.dishModel.price")
    @Mapping(target = "urlPicture", source = "orderDishModel.dishModel.urlPicture")
    @Mapping(target = "active", source = "orderDishModel.dishModel.active")
    @Mapping(target = "amount", source = "orderDishModel.amount")
    DishResponseDto toDishResponseDto(OrderDishModel orderDishModel);
}
