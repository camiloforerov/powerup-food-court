package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements IRestaurantPersistentPort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        this.restaurantRepository.save(this.restaurantEntityMapper.toEntity(restaurantModel));
    }

    @Override
    public RestaurantModel getRestaurantByOwnerEmail(String ownerEmail) {
        List<RestaurantEntity> restaurants = this.restaurantRepository.findByOwnerEmail(ownerEmail);
        return !restaurants.isEmpty() ? this.restaurantEntityMapper.toModelNoDishes(restaurants.get(0)) : null;
    }

    @Override
    public Optional<RestaurantModel> getRestaurantById(Long restaurantId) {
        RestaurantEntity restaurantEntity = this.restaurantRepository.findById(restaurantId).orElse(null);
        return Optional.ofNullable(this.restaurantEntityMapper.toModelNoDishes(restaurantEntity));
    }

    @Override
    public List<RestaurantModel> listByPageAndElements(int pageNumber, int numbersOfElements) {
        PageRequest sortedByNameAsc = PageRequest.of(
                pageNumber,
                numbersOfElements,
                Sort.by("name").ascending()
        );
        Page<RestaurantEntity> restaurants = this.restaurantRepository.findAll(sortedByNameAsc);

        return restaurants.getContent().stream()
                .map(res -> this.restaurantEntityMapper.toModelNoDishes(res))
                .collect(Collectors.toList());
    }
}
