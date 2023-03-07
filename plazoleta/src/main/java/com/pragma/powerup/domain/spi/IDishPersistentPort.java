package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.DishModel;

import java.util.List;
import java.util.Optional;

public interface IDishPersistentPort {
    DishModel saveDish(DishModel dishModel);
    Optional<DishModel> getDishByIdAndRestaurantId(Long dishId, Long restaurantId);
    List<DishModel> getDishesByRestaurantId(Long restaurantId);
    List<DishModel> listDishesByRestaurantPageable(Long restaurantId,
            int pageNumber, int elementsPerPage);
}
