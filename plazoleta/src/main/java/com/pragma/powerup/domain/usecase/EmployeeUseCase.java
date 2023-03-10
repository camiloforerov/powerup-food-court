package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.api.IEmployeeServicePort;
import com.pragma.powerup.domain.exceptions.EmployeeDoesNotBelongToAnyRestaurantException;
import com.pragma.powerup.domain.exceptions.NotificationNotSent;
import com.pragma.powerup.domain.exceptions.OrderDoesNotExistException;
import com.pragma.powerup.domain.exceptions.OrderIsAlreadyAssignedToChefException;
import com.pragma.powerup.domain.model.OrderModel;
import com.pragma.powerup.domain.model.OrderWithDishesModel;
import com.pragma.powerup.domain.model.RestaurantEmployeeModel;
import com.pragma.powerup.domain.model.userservice.UserModel;
import com.pragma.powerup.domain.spi.IMessagingServicePort;
import com.pragma.powerup.domain.spi.IOrderPersistentPort;
import com.pragma.powerup.domain.spi.IRestaurantEmployeePersistentPort;
import com.pragma.powerup.domain.spi.IUserServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
public class EmployeeUseCase implements IEmployeeServicePort {
    private final IOrderPersistentPort orderPersistentPort;
    private final IRestaurantEmployeePersistentPort restaurantEmployeePersistentPort;
    private final IUserServicePort userServicePort;
    private final IMessagingServicePort messagingServicePort;

    /**
     * Gets the orders from employee's restaurant filtered by the specified state
     *
     * @param orderState - Must be already validated
     * @param page - page number to show. For pagination
     * @param elementsPerPage - elements to show per page. For pagination
     * @return list of orders with its dishes
     * */
    @Override
    public List<OrderWithDishesModel> listOrdersByState(String orderState, int page, int elementsPerPage, String employeeEmail) {
        Optional<RestaurantEmployeeModel> employeeModel = this.restaurantEmployeePersistentPort
                .findByEmployeeEmail(employeeEmail);
        if (employeeModel.isEmpty()) {
            throw new EmployeeDoesNotBelongToAnyRestaurantException("Employee doesn't have a restaurant associated");
        }

        return this.orderPersistentPort
                .getOrdersByRestaurantIdAndState(employeeModel.get().getRestaurant().getId(),
                        page,
                        elementsPerPage,
                        orderState);
    }

    /**
     * Assigns a pending order to an employee and changes its state to in preparation
     *
     * @param orderId - order to find
     * @param employeeEmail - employee email to assign to the order
     * @throws EmployeeDoesNotBelongToAnyRestaurantException - employee doesn't have a restaurant assigned
     * @throws OrderDoesNotExistException - order with the id specified couldn't be found
     * */
    @Override
    public OrderWithDishesModel assignOrder(String employeeEmail, Long orderId) {
        // find employee
        Optional<RestaurantEmployeeModel> employeeModel = this.restaurantEmployeePersistentPort
                .findByEmployeeEmail(employeeEmail);
        if (employeeModel.isEmpty()) {
            throw new EmployeeDoesNotBelongToAnyRestaurantException("Employee doesn't have a restaurant associated");
        }
        // find order by id
        Optional<OrderModel> orderModel = orderPersistentPort
                .getOrderByRestaurantIdAndOrderId(employeeModel.get().getRestaurant().getId(), orderId);
        if (orderModel.isEmpty()) {
            throw new OrderDoesNotExistException("Order doesn't exists or can't be taken");
        }
        if (!Objects.equals(orderModel.get().getState(), Constants.ORDER_PENDING_STATE) ||
                orderModel.get().getChef() != null) {
            throw new OrderIsAlreadyAssignedToChefException("Order is already assigned or in process");
        }
        // assign it to order
        orderModel.get().setChef(employeeModel.get());
        orderModel.get().setState(Constants.ORDER_PREPARATION_STATE);
        return orderPersistentPort.saveOrderToOrderWithDishes(orderModel.get());
    }

    /**
     * Change order to ready state, and notifies the client with a pin code generated
     *
     * @param orderId - order id corresponding to the order to change
     * @param employeeEmail - employee email
     * @return the order updated
     * */
    @Override
    public OrderModel changeOrderToReady(Long orderId, String employeeEmail) {
        List<OrderModel> foundOrders;
        String pinGenerated;
        Optional<RestaurantEmployeeModel> restaurantEmployeeModel = this.restaurantEmployeePersistentPort
                .findByEmployeeEmail(employeeEmail);
        if (restaurantEmployeeModel.isEmpty()) {
            throw new EmployeeDoesNotBelongToAnyRestaurantException("Employee is not related to any company");
        }
        Optional<OrderModel> orderModel = this.orderPersistentPort
                .getOrderByRestaurantIdAndOrderId(restaurantEmployeeModel.get().getRestaurant().getId(), orderId);
        if (orderModel.isEmpty()) {
            throw new OrderDoesNotExistException("Order couldn't be found");
        }
        do {
            pinGenerated = this.generatedPinCode();
            foundOrders = orderPersistentPort.getOrdersReadyBySecurityCode(pinGenerated);
        } while (!foundOrders.isEmpty());
        orderModel.get().setState(Constants.ORDER_READY_STATE);
        orderModel.get().setSecurityPin(pinGenerated);
        OrderModel savedOrderModel = orderPersistentPort.saveOrder(orderModel.get());

        UserModel userModel = userServicePort.getUserByEmail(orderModel.get().getClientEmail());

        boolean notificationMade = messagingServicePort
                .notifyClientOrderReady(orderModel.get().getSecurityPin(), userModel.getPhone());
        if (!notificationMade) {
            throw new NotificationNotSent("Notification couldn't be send");
        }
        return savedOrderModel;
    }

    /**
     * Generates a PIN Code of 6 digits randomly
     *
     * @return generatedPinCode
     * */
    private String generatedPinCode() {
        Random rand = new Random();

        int n = rand.nextInt(1000000);
        return String.format("%06d", n);
    }
}
