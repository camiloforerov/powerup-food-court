package com.pragma.powerup.infrastructure.out.feign.mapper;

import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.infrastructure.out.feign.dto.request.CreateEmployeeRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ICreateEmployeeRequestMapper {
    @Mapping(target = "id", source = "userModel.id")
    @Mapping(target = "name", source = "userModel.name")
    @Mapping(target = "lastname", source = "userModel.lastname")
    @Mapping(target = "phone", source = "userModel.phone")
    @Mapping(target = "email", source = "userModel.email")
    @Mapping(target = "password", source = "userModel.password")
    @Mapping(target = "roleId", source = "roleId")
    CreateEmployeeRequestDto toDto(UserModel userModel, Long roleId);

}
