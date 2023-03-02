package com.pragma.powerup.application.mapper.request;

import com.pragma.powerup.application.dto.request.CreateClientRequestDto;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICreateClientRequestMapper {
    @Mapping(target = "id", source = "createClientRequestDto.id")
    @Mapping(target = "name", source = "createClientRequestDto.name")
    @Mapping(target = "lastname", source = "createClientRequestDto.lastname")
    @Mapping(target = "phone", source = "createClientRequestDto.phone")
    @Mapping(target = "email", source = "createClientRequestDto.email")
    @Mapping(target = "password", source = "createClientRequestDto.password")
    UserModel toModel(CreateClientRequestDto createClientRequestDto);
}
