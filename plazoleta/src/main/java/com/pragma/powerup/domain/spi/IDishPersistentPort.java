package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.DishModel;

import java.util.List;

public interface IDishPersistentPort {
    DishModel saveDish(DishModel dishModel);
    DishModel getDishById(Long dishId);
    List<DishModel> getDishesByRestaurantId(Long restaurantId);
    List<DishModel> listDishesByRestaurantPageable(Long restaurantId,
            int pageNumber, int elementsPerPage);
}
