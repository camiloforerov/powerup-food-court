package com.pragma.powerup.infrastructure.configuration.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BearerTokenWrapper {
    private String token;
}
