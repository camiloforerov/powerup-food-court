package com.pragma.powerup.application.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class AssignOrderResponseDto {
    private Long id;
    private Date date;
    private String clientEmail;
    private String state;
    private String chefEmail;
}
