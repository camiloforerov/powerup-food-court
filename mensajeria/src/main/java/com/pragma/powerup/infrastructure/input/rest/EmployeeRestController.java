package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.SendNotificationClientRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.validation.Valid;

@RestController
@RequestMapping("/messaging/v1/employee")
public class EmployeeRestController {
    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String SERVICE_SID = System.getenv("TWILIO_SERVICE_SID");

    @PostMapping("/send-notification")
    public ResponseEntity<Object> sendMessageTest(@RequestBody @Valid SendNotificationClientRequestDto sendNotificationClientRequestDto) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(sendNotificationClientRequestDto.getPhone()),
                SERVICE_SID,
                "Hola, tu código de verificación para reclamar tu pedido es " + sendNotificationClientRequestDto.getSecurityCode()
                )
                .create();
        return ResponseEntity.ok(true);
    }
}
