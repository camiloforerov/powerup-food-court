package com.pragma.powerup.infrastructure.configuration.feign;

import com.pragma.powerup.infrastructure.out.feign.exceptions.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FeignClientConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public BearerTokenWrapper bearerTokenWrapper() {
        return new BearerTokenWrapper();
    }
}
