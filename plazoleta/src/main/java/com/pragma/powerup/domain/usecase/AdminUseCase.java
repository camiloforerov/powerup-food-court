package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IAdminServicePort;
import com.pragma.powerup.domain.exceptions.OwnerAlreadyHasRestaurantException;
import com.pragma.powerup.domain.exceptions.UserDoesNotExistException;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminUseCase implements IAdminServicePort {
    private final IRestaurantPersistentPort restaurantPersistentPort;
    private final IUserServicePort userServicePort;

    /**
     * Creates a restaurant, validating first if the user id correspond to a
     * restaurant owner in the user service
     *
     * @param restaurantModel - restaurant information
     * @throws UserDoesNotExistException - restaurant owner doesn't exist
     * @throws OwnerAlreadyHasRestaurantException - owner already exists
     * */
    @Override
    public void createRestaurant(RestaurantModel restaurantModel) {
        if (!userServicePort.userIsRestaurantOwner(restaurantModel.getOwnerEmail())) {
            throw new UserDoesNotExistException("User doesn't exists");
        }
        RestaurantModel restaurantModel1 = restaurantPersistentPort.
                getRestaurantByOwnerEmail(restaurantModel.getOwnerEmail());
        if (restaurantModel1 != null) {
            throw new OwnerAlreadyHasRestaurantException("Owner already has a restaurant registered");
        }
        this.restaurantPersistentPort.saveRestaurant(restaurantModel);
    }
}
