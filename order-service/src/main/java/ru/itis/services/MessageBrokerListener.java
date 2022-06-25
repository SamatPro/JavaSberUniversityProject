package ru.itis.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.config.BrokerConfiguration;
import ru.itis.dtos.OrderCustomerResponse;
import ru.itis.dtos.OrderRestaurantResponse;
import ru.itis.enums.ResponseStatus;
import ru.itis.models.CustomerValidate;
import ru.itis.models.Order;
import ru.itis.models.RestaurantValidate;
import ru.itis.repositories.OrderRepository;

@Component
public class MessageBrokerListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * This method gets messages from queue {@link BrokerConfiguration#REPLY_RESTAURANT_QUEUE_NAME}
     *
     * @param response DTO message from restaurant-service
     */
    @RabbitListener(queues = BrokerConfiguration.REPLY_RESTAURANT_QUEUE_NAME)
    public void receiveMessageFromRestaurant(OrderRestaurantResponse response) {
        synchronized (response.getOrderId().toString().intern()) {
            Order order = orderService.getById(response.getOrderId());
            order.setRestaurantValidate(
                    new RestaurantValidate(response.isValidate() ? ResponseStatus.RECEIVED : ResponseStatus.REJECTED));
            checkToChangeStatus(order);
        }
    }

    /**
     * This method gets messages from queue {@link BrokerConfiguration#REPLY_CUSTOMER_QUEUE_NAME}
     *
     * @param response DTO message from customer-service
     */
    @RabbitListener(queues = BrokerConfiguration.REPLY_CUSTOMER_QUEUE_NAME)
    public void receiveMessageFromCustomer(OrderCustomerResponse response) {
        synchronized (response.getOrderId().toString().intern()) {
            if (response.getOrderId() != null) {
                Order order = orderService.getById(response.getOrderId());
                if (order != null) {
                    order.setCustomerValidate(
                            new CustomerValidate(response.isValidate() ? ResponseStatus.RECEIVED : ResponseStatus.REJECTED));
                    checkToChangeStatus(order);
                }
            }
        }
    }

    private void checkToChangeStatus(Order order) {
        if (order.getCustomerValidate().getStatus().equals(ResponseStatus.REJECTED)
                || order.getRestaurantValidate().getStatus().equals(ResponseStatus.REJECTED)) {
            order.setStatus("REJECTED");
        } else if (order.getCustomerValidate().getStatus().equals(ResponseStatus.RECEIVED)
                && order.getRestaurantValidate().getStatus().equals(ResponseStatus.RECEIVED)) {
            order.setStatus("IN_PROGRESS");
        }
        orderRepository.save(order);
    }
}