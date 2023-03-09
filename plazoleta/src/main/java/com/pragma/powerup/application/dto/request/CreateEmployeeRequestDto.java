package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateEmployeeRequestDto {
    @NotNull(message = "Category id cannot be empty")
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 150, message = "Name must be between 1 and 150 characters")
    private String name;
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 1, max = 150, message = "Last name must be between 1 and 150 characters")
    private String lastname;
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Pattern not allowed")
    @Size(min = 7,
            max = 13,
            message = "Phone number must be between 7 and 13 characters")
    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;
    @NotBlank(message = "Password cannot be null")
    @Size(min = 3, max = 100, message = "Password must be between 3 and 100 characters")
    private String password;

}
