package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.CreateClientRequestDto;
import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtTokenResponseDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.application.handler.IClientHandler;
import com.pragma.powerup.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final IUserHandler userHandler;
    private final IAuthHandler authHandler;
    private final IClientHandler clientHandler;

    @Operation(summary = "Login into the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized for bad credentials", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> loginUser(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        return new ResponseEntity<>(this.authHandler.loginUser(loginRequestDto), HttpStatus.OK);
    }

    @Operation(summary = "Find a user by its email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUserByEmail(@RequestParam("email") String email) {
        return new ResponseEntity<>(this.userHandler.getUserByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Creates a client account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login successful", content = @Content),
            @ApiResponse(responseCode = "404", description = "Role client not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "User with email already exists", content = @Content)
    })
    @PostMapping("/client")
    public ResponseEntity<Void> createClientAccount(@RequestBody @Valid CreateClientRequestDto createClientRequestDto) {
        this.clientHandler.createClientAccount(createClientRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
