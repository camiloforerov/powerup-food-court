package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtTokenResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.infrastructure.configuration.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.pragma.powerup.infrastructure.configuration.security.jwt.JwtUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtTokenResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtTokenResponseDto(jwt, userDetails.getUsername(), roles);
    }
}
