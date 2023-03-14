package com.pragma.powerup.infrastructure.out.feign.exceptions;

import com.pragma.powerup.infrastructure.exceptions.exception.BadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class MessagingServiceErrorDecoder implements ErrorDecoder{
    private final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400:
                return new BadRequestException("Incorrect data to send message");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
