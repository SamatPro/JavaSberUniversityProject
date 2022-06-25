package ru.itis.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.config.BrokerConfiguration;
import ru.itis.dtos.OrderCustomerDto;
import ru.itis.dtos.OrderResponse;

@Component
public class MessageBrokerListener {

    @Autowired
    private MessageBrokerSender messageBrokerSender;

    @Autowired
    private CustomersService customersService;

    /**
     * This method gets messages from queue {@link BrokerConfiguration#CUSTOMER_QUEUE_NAME} and
     * sends back to order-service
     *
     * @param order DTO message from order-service
     */
    @RabbitListener(queues = BrokerConfiguration.CUSTOMER_QUEUE_NAME)
    private void receiveMessageFromOrder(OrderCustomerDto order) {
        if (order.getUserId() != null) {
            boolean isValidate = customersService.checkBlockList(order.getUserId());
            OrderResponse response = new OrderResponse(order.getOrderId(), isValidate);
            messageBrokerSender.sendReply(response);
        } else {
            OrderResponse response = new OrderResponse(order.getOrderId(), false);
            messageBrokerSender.sendReply(response);
        }
    }
}