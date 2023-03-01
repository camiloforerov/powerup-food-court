package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.DishModel;

public interface IDishPersistentPort {
    DishModel saveDish(DishModel dishModel);
    DishModel getDishById(Long dishId);
}
