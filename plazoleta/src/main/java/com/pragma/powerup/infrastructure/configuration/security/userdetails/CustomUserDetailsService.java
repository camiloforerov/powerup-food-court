package com.pragma.powerup.infrastructure.configuration.security.userdetails;

import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserServicePort userServicePort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userServicePort.getUserByEmail(email);
        if (userModel == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }
        return CustomUserDetails.build(userModel);
    }
}
