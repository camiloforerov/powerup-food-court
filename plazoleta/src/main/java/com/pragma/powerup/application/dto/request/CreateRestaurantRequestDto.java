package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateRestaurantRequestDto {
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "(?!^\\d+$)(?!^\\d+( \\d+)*$)^\\w+( \\w+)*$")
    private String name;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotBlank(message = "Owner email cannot be blank")
    private String ownerEmail;
    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Pattern not allowed")
    @Size(min = 7,
            max = 13,
            message = "Phone number must be between 7 and 13 characters")
    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
    @NotBlank(message = "Url logo cannot be blank")
    private String urlLogo;
    @NotNull(message = "Nit cannot be blank")
    private Long nit;
}
