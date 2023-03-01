package com.pragma.powerup.infrastructure.out.feign.exceptions;

import com.pragma.powerup.infrastructure.exceptions.exception.AuthenticationException;
import com.pragma.powerup.infrastructure.exceptions.exception.DataAlreadyExistsException;
import com.pragma.powerup.infrastructure.exceptions.exception.NoDataFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Custom error decoder to handle feign client errors
 * */
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 401:
                return new AuthenticationException("Authorization failed when trying to connect to user service");
            case 404:
                return new NoDataFoundException("Not found");
            case 409:
                return new DataAlreadyExistsException("Data already exists");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
