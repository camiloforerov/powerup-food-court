package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderStateType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Named("orderEntityMapper")
@Mapper(componentModel = "spring",
        uses = { IRestaurantEntityMapper.class, IDishEntityMapper.class },
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    @Named("toModel")
    @Mapping(target = "id", source = "orderEntity.id")
    @Mapping(target = "date", source = "orderEntity.date")
    @Mapping(target = "clientEmail", source = "orderEntity.clientEmail")
    @Mapping(target = "restaurant", qualifiedByName = {"restaurantEntityMapper", "toModelNoDishes"})
    @Mapping(target = "chef", source = "orderEntity.restaurantEmployee")
    @Mapping(target = "state", source = "orderEntity.state", qualifiedByName = "stateEnumToString")
    OrderModel toModel(OrderEntity orderEntity);

    @Named("toEntity")
    @Mapping(target = "id", source = "orderModel.id")
    @Mapping(target = "date", source = "orderModel.date")
    @Mapping(target = "clientEmail", source = "orderModel.clientEmail")
    @Mapping(target = "state", source = "orderModel.state", qualifiedByName = "stateStringToEnum")
    @Mapping(target = "restaurant", source = "orderModel.restaurant")
    @Mapping(target = "restaurantEmployee", source = "orderModel.chef")
    OrderEntity toEntity(OrderModel orderModel);

    @Mapping(target = "id", source = "orderEntity.id")
    @Mapping(target = "date", source = "orderEntity.date")
    @Mapping(target = "clientEmail", source = "orderEntity.clientEmail")
    @Mapping(target = "state", source = "orderEntity.state", qualifiedByName = "stateEnumToString")
    @Mapping(target = "orderDishes", source = "orderEntity.orderDishes", qualifiedByName = {"toOrderDishModel"})
    OrderWithDishesModel toOrderWithDishesModel(OrderEntity orderEntity);

    @Named("toOrderDishModel")
    @Mapping(target = "dishModel", source = "orderDishEntity.dish", qualifiedByName = {"dishEntityMapper", "toModel"})
    @Mapping(target = "orderModel", ignore = true)
    @Mapping(target = "amount", source = "orderDishEntity.amount")
    OrderDishModel toOrderDishModel(OrderDishEntity orderDishEntity);

    @Named("stateStringToEnum")
    static OrderStateType stateStringToEnum(String stateString) {
        switch (stateString) {
            case "PENDING":
                return OrderStateType.PENDIENTE;
            case "PREPARATION":
                return OrderStateType.EN_PREPARACION;
            case "CANCELED":
                return OrderStateType.CANCELADO;
            case "READY":
                return OrderStateType.LISTO;
            case "DELIVERED":
                return OrderStateType.ENTREGADO;
            default:
                return OrderStateType.CANCELADO;
        }
    }

    @Named("stateEnumToString")
    static String stateEnumToString(OrderStateType orderStateType) {
        switch (orderStateType) {
            case PENDIENTE:
                return "PENDING";
            case EN_PREPARACION:
                return "PREPARATION";
            case CANCELADO:
                return "CANCELED";
            case LISTO:
                return "READY";
            case ENTREGADO:
                return "DELIVERED";
            default:
                return "CANCELED";
        }
    }
}
