package com.pragma.powerup.infrastructure.configuration.security;

import com.pragma.powerup.infrastructure.configuration.feign.BearerTokenWrapper;
import com.pragma.powerup.infrastructure.configuration.security.jwt.JwtAuthorizationFilter;
import com.pragma.powerup.infrastructure.configuration.security.jwt.JwtUtils;
import com.pragma.powerup.infrastructure.configuration.security.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final BearerTokenWrapper bearerTokenWrapper;

    @Bean
    public JwtAuthorizationFilter authenticationJwtTokenFilter(JwtUtils jwtUtils,
                                                               CustomUserDetailsService userDetailsService,
                                                               BearerTokenWrapper tokenWrapper
    ) {
        return new JwtAuthorizationFilter(jwtUtils, userDetailsService, tokenWrapper);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .antMatchers("/food-court/v1/owner/**").hasRole("OWNER")
                .antMatchers("/food-court/v1/admin/**").hasRole("ADMIN")
                .antMatchers("/food-court/v1/client/**").hasRole("CLIENT")
                .antMatchers("/food-court/v1/employee/**").hasRole("EMPLOYEE")
                .anyRequest()
                .authenticated();

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(jwtUtils, userDetailsService, bearerTokenWrapper),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(customUserDetailsService);

        return authProvider;
    }
}
