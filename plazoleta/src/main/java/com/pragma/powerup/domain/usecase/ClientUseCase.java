package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.IClientServicePort;
import com.pragma.powerup.domain.exceptions.ClientAlreadyHasOrderInProcessException;
import com.pragma.powerup.domain.exceptions.DishDoesNotExistException;
import com.pragma.powerup.domain.exceptions.DishesCannotBeEmptyException;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderStateCannotChangeException;
import com.pragma.powerup.domain.exceptions.RestaurantDoesNotExistException;
import com.pragma.powerup.domain.model.CategoryWithDishesModel;
import com.pragma.powerup.domain.model.DishModel;
import com.pragma.powerup.domain.model.OrderDishModel;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.RestaurantModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IDishPersistentPort;
import com.pragma.powerup.domain.spi.IMessagingServicePort;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantPersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {
    private final IRestaurantPersistentPort restaurantPersistentPort;
    private final IDishPersistentPort dishPersistentPort;
    private final IOrderPersistentPort orderPersistentPort;
    private final IMessagingServicePort messagingServicePort;
    private final IUserServicePort userServicePort;

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

    /**
     * Create a new order verifying first the client doesn't have one already in process
     *
     * @param dishesOrderData - dishes the client wants
     * @param restaurantId - the restaurant the dishes belongs to
     * @param clientEmail - client email
     * @throws RestaurantDoesNotExistException - restaurant doesn't exists
     * @throws ClientAlreadyHasOrderInProcessException - the client already has an order in process
     * */
    @Override
    public List<OrderDishModel> newOrder(List<OrderDishModel> dishesOrderData,
                                    Long restaurantId,
                                    String clientEmail
    ) {
        if (dishesOrderData.isEmpty()) {
            throw new DishesCannotBeEmptyException("Dishes cannot be empty");
        }
        Optional<RestaurantModel> restaurantModel = this.restaurantPersistentPort.getRestaurantById(restaurantId);
        if (restaurantModel.isEmpty()) {
            throw new RestaurantDoesNotExistException("Restaurant not found");
        }
        if (!this.orderPersistentPort.getInProcessOrdersByClientEmail(clientEmail).isEmpty()) {
            throw new ClientAlreadyHasOrderInProcessException("Client already has an order in process");
        }
        OrderModel savedOrderModel = this.saveOrderModel(clientEmail,
                new Date(), restaurantModel.get(), Constants.ORDER_PENDING_STATE);

        for (OrderDishModel orderDishData: dishesOrderData) {
            orderDishData.setOrderModel(savedOrderModel);
            this.orderPersistentPort.saveOrderDish(orderDishData);
        }
        return dishesOrderData;
    }

    private OrderModel saveOrderModel(String clientEmail,
                                      Date date,
                                      RestaurantModel restaurantModel,
                                      String state) {
        OrderModel orderModel = new OrderModel();
        orderModel.setClientEmail(clientEmail);
        orderModel.setDate(date);
        orderModel.setRestaurant(restaurantModel);
        orderModel.setState(state);
        return this.orderPersistentPort.saveOrder(orderModel);
    }

    /**
     * Gets the dish model by its id and restaurant id
     *
     * @param dishId - dish id
     * @throws DishDoesNotExistException the dish with the specified id doesn't exist
     */
    @Override
    public DishModel getDishModelByIdAndRestaurantId(Long dishId, Long restaurantId) {
        Optional<DishModel> dishModel = this.dishPersistentPort.getDishByIdAndRestaurantId(dishId, restaurantId);
        if (dishModel.isEmpty()) {
            throw new DishDoesNotExistException("Dish doesn't exist");
        }
        return dishModel.get();
    }

    /**
     * Cancel an order with its id and has to belong the client email
     *
     * @param orderId order id to be cancelled
     * @param clientEmail client email
     * @throws OrderDoesNotExistException the order was not found
     * */
    @Override
    public void cancelOrder(Long orderId, String clientEmail) {
        Optional<OrderModel> orderModel = orderPersistentPort.getOrderById(orderId);
        if (orderModel.isEmpty() || !Objects.equals(orderModel.get().getClientEmail(), clientEmail)) {
            throw new OrderDoesNotExistException("Order doesn't exists");
        }
        if (!orderModel.get().getState().equals(Constants.ORDER_PENDING_STATE)) {
            UserModel userModel = this.userServicePort.getUserByEmail(clientEmail);
            this.messagingServicePort.notifyClient(Constants.CANCEL_ORDER_ERROR, userModel.getPhone());
            throw new OrderStateCannotChangeException("Order cannot be cancelled");
        }
        orderModel.get().setState(Constants.ORDER_CANCELED_STATE);
        orderPersistentPort.saveOrder(orderModel.get());
    }
}
