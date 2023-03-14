package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SendNotificationClientRequestDto;
import com.pragma.powerup.application.handler.INotificationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/messaging/v1/employee")
@RequiredArgsConstructor
public class EmployeeRestController {
    private final INotificationHandler notificationHandler;

    @Operation(summary = "Send notification to phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification sent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Notification couldn't be sent", content = @Content)
    })
    @PostMapping("/send-notification")
    public ResponseEntity<Boolean> sendMessageTest(@RequestBody @Valid SendNotificationClientRequestDto sendNotificationClientRequestDto) {
        return new ResponseEntity<>(this.notificationHandler.notifyClient(sendNotificationClientRequestDto), HttpStatus.OK);
    }
}
