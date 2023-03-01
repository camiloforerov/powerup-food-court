package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IOwnerServicePort;
import com.pragma.powerup.domain.exceptions.CategoryDoesNotExistException;
import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.exceptions.NoRestaurantForOwnerFoundException;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.model.CategoryModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.*;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class OwnerUseCase implements IOwnerServicePort {
    private final IDishPersistentPort dishPersistentPort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistentPort restaurantPersistentPort;
    private final IUserServicePort userServicePort;
    private final IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;

    /**
     * Create a restaurant dish
     *
     * @param dishModel - dish information
     * @param restaurantModel - restaurant to be assigned the new dish
     * @param categoryId - category Id
     * @throws CategoryDoesNotExistException - category doesn't exist
     *
     * @return the dish model with active state and id generated
     * */
    @Override
    public DishModel createDish(DishModel dishModel, RestaurantModel restaurantModel, Long categoryId) {
        dishModel.setRestaurant(restaurantModel);
        CategoryModel categoryModel = this.categoryPersistencePort.getCategoryById(categoryId);
        if (categoryModel == null) {
            throw new CategoryDoesNotExistException("Category doesn't exists");
        }
        dishModel.setCategory(categoryModel);
        dishModel.setActive(true);

        return this.dishPersistentPort.saveDish(dishModel);
    }

    /**
     * Updates a dish with optional parameters of price and/or description
     *
     * @param dishId - dish id
     * @param description - optional description
     * @param price - optional price
     * */
    @Override
    public DishModel updateDish(Long dishId, Double price, String description) {
        DishModel dishModel = this.dishPersistentPort.getDishById(dishId);
        if (dishModel == null) {
            throw new DishDoesNotExistException("Dish doesn't exists");
        }
        if (price != null) {
            dishModel.setPrice(price);
        }
        if (description != null) {
            dishModel.setDescription(description);
        }
        return this.dishPersistentPort.saveDish(dishModel);
    }

    /**
     * Get the restaurant by owner
     *
     * @param ownerEmail - owner email
     * @throws NoRestaurantForOwnerFoundException - No restaurant was found for the owner id
     * */
    @Override
    public RestaurantModel getRestaurantByOwnerEmail(String ownerEmail) {
        RestaurantModel restaurantModel = this.restaurantPersistentPort.getRestaurantByOwnerEmail(ownerEmail);
        if (restaurantModel == null) {
            throw new NoRestaurantForOwnerFoundException("No restaurant found for the owner id specified");
        }
        return restaurantModel;
    }

    /**
     * Creates an employee
     *
     * @param  userModel - employee information
     * @param roleId - employee id role
     * @param restaurantId - restaurant to be linked with the employee
     * */
    @Override
    public RestaurantEmployeeModel createEmployee(UserModel userModel, Long roleId, Long restaurantId) {
        Optional<RestaurantModel> restaurantModel = this.restaurantPersistentPort.getRestaurantById(restaurantId);
        if (restaurantModel.isEmpty()) {
            throw new RestaurantDoesNotExistException("Restaurant not found");
        }
        UserModel createdUserModel = this.userServicePort.createEmployee(userModel, roleId);

        return this.restaurantEmployeePersistentPort.save(new RestaurantEmployeeModel(
                createdUserModel.getEmail(), restaurantModel.get())
        );
    }
}
