package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IAdminServicePort;
import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.api.IOwnerServicePort;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IAuthPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolePersistentPort;
import com.pragma.powerup.domain.spi.IUserPersistentPort;
import com.pragma.powerup.domain.usecase.AdminUseCase;
import com.pragma.powerup.domain.usecase.ClientUseCase;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RolePersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserPersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;

    @Bean
    public IAuthPasswordEncoderPort authPasswordEncoderPort() {
        return new AuthBcryptAdapter(encoder());
    }

    @Bean
    public IUserPersistentPort userPersistentPort() {
        return new UserPersistenceAdapter(this.userEntityMapper,
                this.userRepository, this.roleEntityMapper);
    }

    @Bean
    public IRolePersistentPort rolePersistentPort() {
        return new RolePersistenceAdapter(this.roleRepository, this.roleEntityMapper);
    }

    @Bean
    public IAdminServicePort adminServicePort() {
        return new AdminUseCase(this.authPasswordEncoderPort(),
                this.userPersistentPort(), this.rolePersistentPort());
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(this.userPersistentPort(), this.rolePersistentPort());
    }

    @Bean
    public IOwnerServicePort ownerServicePort() {
        return new OwnerUseCase(this.userPersistentPort(), this.rolePersistentPort(), this.authPasswordEncoderPort());
    }

    @Bean
    public IClientServicePort clientServicePort() {
        return new ClientUseCase(this.userPersistentPort(), this.rolePersistentPort(),
                this.authPasswordEncoderPort());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}