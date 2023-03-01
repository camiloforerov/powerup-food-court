package com.pragma.powerup.infrastructure.out.feign.mapper;

import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.infrastructure.out.feign.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserResponseMapper {
    UserModel toModel(UserResponseDto userResponseDto);
}
