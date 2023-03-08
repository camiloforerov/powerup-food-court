package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.response.AssignOrderResponseDto;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAssignOrderResponseMapper {
    @Mapping(target = "id", source = "orderModel.id")
    @Mapping(target = "date", source = "orderModel.date")
    @Mapping(target = "clientEmail", source = "orderModel.clientEmail")
    @Mapping(target = "state", source = "orderModel.state")
    @Mapping(target = "chefEmail", source = "orderModel.chefEmail")
    AssignOrderResponseDto toDto(OrderWithDishesModel orderModel);
}
