package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {
    private final IRestaurantPersistentPort restaurantPersistentPort;

    /**
     * List restaurants
     *
     * @param pageNumber - number of the page of the restaurants
     * @param numbersOfElements - max number of restaurants to be returned in the list
     * @return list of the restaurants sorted alphabetically
     * */
    @Override
    public List<RestaurantModel> listRestaurants(int pageNumber, int numbersOfElements) {
        return restaurantPersistentPort.listByPageAndElements(pageNumber, numbersOfElements);
    }
}
