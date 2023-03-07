package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = { IOrderEntityMapper.class, IDishEntityMapper.class },
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishEntityMapper {

    @Mapping(target = "dish", source = "orderDishModel.dishModel")
    @Mapping(target = "order", source = "orderDishModel.orderModel", qualifiedByName = {"orderEntityMapper", "toEntity"})
    @Mapping(target = "amount", source = "orderDishModel.amount")
    OrderDishEntity toEntity(OrderDishModel orderDishModel);

    @Mapping(target = "dishModel", source = "orderDishEntity.dish", qualifiedByName = {"dishEntityMapper", "toModel"})
    @Mapping(target = "orderModel", source = "orderDishEntity.order", qualifiedByName = {"orderEntityMapper", "toModel"})
    @Mapping(target = "amount", source = "orderDishEntity.amount")
    OrderDishModel toModel(OrderDishEntity orderDishEntity);
}
