package com.pragma.powerup.infrastructure.input.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        final List<CustomFieldError> customFieldErrors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            final String field = fieldError.getField();
            final String message = fieldError.getDefaultMessage();
            final CustomFieldError customFieldError = new CustomFieldError(field, message);
            customFieldErrors.add(customFieldError);
        }
        return new ResponseEntity<>(customFieldErrors, BAD_REQUEST);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class CustomFieldError {
        private String field;
        private String message;
    }
}
