package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class CreateClientRequestDto {
    @NotNull(message = "Identification cannot be blank")
    @Valid
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @Valid
    private String name;
    @NotBlank(message = "Last name cannot be blank")
    @Valid
    private String lastname;
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Pattern not allowed")
    @Size(min = 7,
            max = 13,
            message = "Phone number must be between 7 and 13 characters")
    @NotBlank(message = "Phone number cannot be blank")
    @Valid
    private String phone;
    @NotBlank(message = "Email must not be blank")
    //@Email(message = "Email must be valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @Email(message = "Email must be valid")
    @Valid
    private String email;
    @NotBlank(message = "Password cannot be null")
    @Valid
    private String password;
}
