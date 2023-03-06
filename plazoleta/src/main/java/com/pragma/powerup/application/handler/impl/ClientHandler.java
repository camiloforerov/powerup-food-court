package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.response.CategorizedDishResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IClientHandler;
import com.pragma.powerup.application.mapper.IListDishesPerRestaurantMapper;
import com.pragma.powerup.application.mapper.IListRestaurantsClientMapper;
import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.model.CategoryWithDishesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientHandler implements IClientHandler {
    private final IClientServicePort clientServicePort;
    private final IListRestaurantsClientMapper listRestaurantsClientMapper;
    private final IListDishesPerRestaurantMapper listDishesPerRestaurantMapper;

    /**
     * Paginated list of restaurants
     *
     * @param page - page number to list
     * @param numberOfElements - elements to show per page
     * @return list paginated and alphabetically sorted
     * */
    @Override
    public List<RestaurantForClientResponseDto> listRestaurants(int page, int numberOfElements) {
        return clientServicePort.listRestaurants(page, numberOfElements).stream()
                .map(restaurant -> listRestaurantsClientMapper.toDto(restaurant))
                .collect(Collectors.toList());
    }

    /**
     * List of dishes of a specific restaurant paginated and grouped by category
     *
     * @param page - page number to list
     * @param elementsPerPage - elements to show per page
     * @return list of categories with corresponding dishes
     */
    @Override
    public List<CategorizedDishResponseDto> listRestaurantCategorizedDishes(
            Long restaurantId, int page, int elementsPerPage) {
        List<CategoryWithDishesModel> categoriesDishes;
        try {
            categoriesDishes = this.clientServicePort.listRestaurantDishesCategorized(restaurantId, page, elementsPerPage);
        } catch (RestaurantDoesNotExistException ex) {
            throw new NoDataFoundException(ex.getMessage());
        }
        return categoriesDishes.stream()
                .map(categoryDishes -> this.listDishesPerRestaurantMapper.toDto(categoryDishes))
                .collect(Collectors.toList());
    }

}
