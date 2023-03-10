package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.IAdminServicePort;
import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.api.IOwnerServicePort;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.AdminUseCase;
import com.pragma.powerup.domain.usecase.ClientUseCase;
import com.pragma.powerup.domain.usecase.EmployeeUseCase;
import com.pragma.powerup.domain.usecase.OwnerUseCase;
import com.pragma.powerup.infrastructure.out.feign.MessagingServiceFeignClient;
import com.pragma.powerup.infrastructure.out.feign.UserServiceFeignClient;
import com.pragma.powerup.infrastructure.out.feign.adapter.MessagingServiceAdapter;
import com.pragma.powerup.infrastructure.out.feign.adapter.UserServiceAdapter;
import com.pragma.powerup.infrastructure.out.feign.mapper.ICreateEmployeeRequestMapper;
import com.pragma.powerup.infrastructure.out.feign.mapper.ICreateEmployeeResponseMapper;
import com.pragma.powerup.infrastructure.out.feign.mapper.IUserResponseMapper;
import com.pragma.powerup.infrastructure.out.jpa.adapter.CategoryPersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.DishPersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.OrderPersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantEmployeePersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestaurantPersistenceAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IOrderEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEmployeeEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IOrderRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantEmployeeRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IDishRepository dishRepository;
    private final ICategoryRepository categoryRepository;
    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final ICreateEmployeeRequestMapper createEmployeeRequestMapper;
    private final ICreateEmployeeResponseMapper createEmployeeResponseMapper;
    private final IUserResponseMapper userResponseMapper;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final UserServiceFeignClient userServiceFeignClient;
    private final MessagingServiceFeignClient messagingServiceFeignClient;

    @Bean
    public IRestaurantPersistentPort restaurantPersistentPort() {
        return new RestaurantPersistenceAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IDishPersistentPort dishPersistentPort() {
        return new DishPersistenceAdapter(dishRepository, dishEntityMapper);
    }

    public IOrderPersistentPort orderPersistentPort() {
        return new OrderPersistenceAdapter(orderRepository, orderDishRepository, orderEntityMapper, orderDishEntityMapper);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort() {
        return new RestaurantEmployeePersistenceAdapter(restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserServiceAdapter(userServiceFeignClient,
                createEmployeeRequestMapper,
                createEmployeeResponseMapper,
                userResponseMapper);
    }

    @Bean
    public IMessagingServicePort messagingServicePort() {
        return new MessagingServiceAdapter(messagingServiceFeignClient);
    }

    @Bean
    public IOwnerServicePort ownerServicePort() {
        return new OwnerUseCase(dishPersistentPort(),
                categoryPersistencePort(),
                restaurantPersistentPort(),
                this.userServicePort(),
                this.restaurantEmployeePersistentPort());
    }

    @Bean
    public IClientServicePort clientServicePort() {
        return new ClientUseCase(this.restaurantPersistentPort(), this.dishPersistentPort(), this.orderPersistentPort());
    }

    @Bean
    public IAdminServicePort adminServicePort() {
        return new AdminUseCase(this.restaurantPersistentPort(), this.userServicePort());
    }

    @Bean
    public IEmployeeServicePort employeeServicePort() {
        return new EmployeeUseCase(this.orderPersistentPort(), this.restaurantEmployeePersistentPort(),
                this.userServicePort(), this.messagingServicePort());
    }
}