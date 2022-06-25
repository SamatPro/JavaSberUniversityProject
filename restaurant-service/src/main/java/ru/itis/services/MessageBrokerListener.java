package ru.itis.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.config.BrokerConfiguration;
import ru.itis.dtos.OrderDishDto;

@Component
public class MessageBrokerListener {

    @Autowired
    private MessageBrokerSender messageBrokerSender;

    @Autowired
    private OrderValidateService orderValidateService;

    /**
     * This method gets messages from queue {@link BrokerConfiguration#RESTAURANT_QUEUE_NAME} and
     * sends back to order-service
     *
     * @param order DTO message from order-service
     */
    @RabbitListener(queues = BrokerConfiguration.RESTAURANT_QUEUE_NAME)
    private void receiveMessageFromOrder(OrderDishDto order) {
        messageBrokerSender.sendReply(orderValidateService.validate(order));
    }
}