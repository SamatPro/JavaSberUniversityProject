package ru.itis.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.itis.config.BrokerConfiguration;
import ru.itis.dtos.OrderCustomerDto;
import ru.itis.dtos.OrderDishDto;

@Component
public class MessageBrokerSenderImpl implements MessageBrokerSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Send message asynchronously to exchange  {@link BrokerConfiguration#DIRECT_EXCHANGE_NAME}
     * with routing key  {@link BrokerConfiguration#RESTAURANT_ROUTING_KEY}
     *
     * @param order message DTO for restaurant-service
     */
    @Async
    @Override
    public void sendMessageToRestaurant(OrderDishDto order) {
        rabbitTemplate.convertAndSend(BrokerConfiguration.DIRECT_EXCHANGE_NAME,
                BrokerConfiguration.RESTAURANT_ROUTING_KEY, order);
    }

    /**
     * Send message asynchronously to exchange  {@link BrokerConfiguration#DIRECT_EXCHANGE_NAME}
     * with routing key  {@link BrokerConfiguration#CUSTOMER_ROUTING_KEY}
     *
     * @param order message DTO for customer-service
     */
    @Async
    @Override
    public void sendMessageToCustomer(OrderCustomerDto order) {
        rabbitTemplate.convertAndSend(BrokerConfiguration.DIRECT_EXCHANGE_NAME,
                BrokerConfiguration.CUSTOMER_ROUTING_KEY, order);
    }
}