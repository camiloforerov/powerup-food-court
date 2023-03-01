package com.pragma.powerup.infrastructure.configuration.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";
    private final BearerTokenWrapper bearerTokenWrapper;

    @Override
    public void apply(RequestTemplate template) {
        if (this.bearerTokenWrapper.getToken() != null) {
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, this.bearerTokenWrapper.getToken()));
        }
    }
}
