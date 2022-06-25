package ru.itis.services;

import ru.itis.dtos.OrderCustomerDto;
import ru.itis.dtos.OrderDishDto;

public interface MessageBrokerSender {
    void sendMessageToRestaurant(OrderDishDto order);

    void sendMessageToCustomer(OrderCustomerDto order);
}