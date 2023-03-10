package com.pragma.powerup.infrastructure.out.feign.exceptions;

import com.pragma.powerup.infrastructure.exceptions.exception.NoDataFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class MessagingServiceErrorDecoder implements ErrorDecoder{
    private final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new NoDataFoundException("Data no found");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
