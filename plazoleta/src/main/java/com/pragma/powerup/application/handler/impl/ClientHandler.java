package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.DishOrderRequestDto;
import com.pragma.powerup.application.dto.request.NewOrderDishRequestDto;
import com.pragma.powerup.application.dto.response.CategorizedDishResponseDto;
import com.pragma.powerup.application.dto.response.DishOrderResponseDto;
import com.pragma.powerup.application.dto.response.NewOrderDishResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantForClientResponseDto;
import com.pragma.powerup.application.exception.exception.BadRequestException;
import com.pragma.powerup.application.exception.exception.DataAlreadyExistsException;
import com.pragma.powerup.application.exception.exception.NoDataFoundException;
import com.pragma.powerup.application.handler.IClientHandler;
import com.pragma.powerup.application.mapper.IListDishesPerRestaurantMapper;
import com.pragma.powerup.application.mapper.IListRestaurantsClientMapper;
import com.pragma.powerup.application.mapper.INewOrderResponseMapper;
import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.exceptions.ClientAlreadyHasOrderInProcessException;
import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.exceptions.DishesCannotBeEmptyException;
import com.pragma.powerup.domain.exceptions.NoRestaurantForOwnerFoundException;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.model.CategoryWithDishesModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientHandler implements IClientHandler {
    private final IClientServicePort clientServicePort;
    private final IListRestaurantsClientMapper listRestaurantsClientMapper;
    private final IListDishesPerRestaurantMapper listDishesPerRestaurantMapper;
    private final INewOrderResponseMapper newOrderResponseMapper;

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

    /**
     * Creates a new order for a client
     *
     * @param newOrderDishRequestDto - dishes information
     * @return list of dishes in the order and the amount of each one
     * @throws NoDataFoundException - dish doesn't exists
     * @throws ClientAlreadyHasOrderInProcessException - client has already an order in process
     * */
    @Override
    public List<DishOrderResponseDto> createNewOrderClient(NewOrderDishRequestDto newOrderDishRequestDto) {
        List<OrderDishModel> orderDishesModels = new ArrayList<>();
        String clientEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        for (DishOrderRequestDto dishOrderDto: newOrderDishRequestDto.getDishes()) {
            try {
                DishModel dishModelFound = this.clientServicePort.getDishModelByIdAndRestaurantId(
                        dishOrderDto.getDishId(),
                        newOrderDishRequestDto.getRestaurantId());
                OrderDishModel orderDishModel = new OrderDishModel(dishModelFound, null, dishOrderDto.getAmount());
                orderDishesModels.add(orderDishModel);
            } catch (DishDoesNotExistException ex ) {
                throw new NoDataFoundException(ex.getMessage());
            } catch (ClientAlreadyHasOrderInProcessException ex) {
                throw new DataAlreadyExistsException(ex.getMessage());
            } catch (DishesCannotBeEmptyException ex) {
                throw new BadRequestException(ex.getMessage());
            }
        }

        List<OrderDishModel> orderDishModels = this.clientServicePort
                .newOrder(orderDishesModels, newOrderDishRequestDto.getRestaurantId(), clientEmail);

        return orderDishModels.stream()
                .map(this.newOrderResponseMapper::toDto)
                .collect(Collectors.toList());
    }
}
