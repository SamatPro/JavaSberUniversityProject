package ru.itis.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.config.BrokerConfiguration;
import ru.itis.dtos.OrderResponse;

@Component
public class MessageBrokerSenderImpl implements MessageBrokerSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendReply(OrderResponse response) {
        rabbitTemplate.convertAndSend(BrokerConfiguration.DIRECT_EXCHANGE_NAME,
                BrokerConfiguration.CUSTOMER_ROUTING_KEY, response);
    }
}