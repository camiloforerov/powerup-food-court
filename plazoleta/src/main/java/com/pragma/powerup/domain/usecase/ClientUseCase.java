package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.model.CategoryWithDishesModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {
    private final IRestaurantPersistentPort restaurantPersistentPort;
    private final IDishPersistentPort dishPersistentPort;

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

    /**
     * Lists dishes of a specific restaurant, paginated, and grouped by category
     *
     * @param restaurantId - restaurant id
     * @param pageNumber - number of page of the dishes
     * @param elementsPerPage - max number of dishes to be returned in the list
     * */
    @Override
    public List<CategoryWithDishesModel> listRestaurantDishesCategorized(Long restaurantId, int pageNumber, int elementsPerPage) {

        Optional<RestaurantModel> restaurantModel = restaurantPersistentPort.getRestaurantById(restaurantId);
        if (restaurantModel.isEmpty()) {
            throw new RestaurantDoesNotExistException("Restaurant doesn't exists");
        }
        List<DishModel> dishes = this.dishPersistentPort.listDishesByRestaurantPageable(
                restaurantId, pageNumber, elementsPerPage);

        return this.groupDishesByCategory(dishes);
    }

    /**
     * Groups a list of dishes by category
     *
     * @param dishes - dishes, each dish must have information about its category
     * @return list of categories with the list of dishes it contains
     * */
    private List<CategoryWithDishesModel> groupDishesByCategory(List<DishModel> dishes) {
        Map<Long, List<DishModel>> dishesMap = dishes.stream()
                .collect(Collectors.groupingBy(dishModel -> dishModel.getCategory().getId(),
                        Collectors.mapping(dishModel -> dishModel, Collectors.toList())));

        List<CategoryWithDishesModel> categories = new ArrayList<>();
        dishesMap.forEach((categoryId, dishModels) -> {
            CategoryWithDishesModel category = new CategoryWithDishesModel();
            category.setId(dishModels.get(0).getCategory().getId());
            category.setName(dishModels.get(0).getCategory().getName());
            category.setDescription(dishModels.get(0).getCategory().getDescription());
            category.setDishes(dishModels);
            categories.add(category);
        });
        return categories;
    }
}
