package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtTokenResponseDto;

public interface IAuthHandler {
    JwtTokenResponseDto loginUser(LoginRequestDto loginRequestDto);
}
