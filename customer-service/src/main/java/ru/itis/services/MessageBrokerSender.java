package ru.itis.services;

import ru.itis.dtos.OrderResponse;

public interface MessageBrokerSender {
    void sendReply(OrderResponse response);
}