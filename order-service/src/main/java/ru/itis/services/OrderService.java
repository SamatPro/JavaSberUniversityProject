package ru.itis.services;

import ru.itis.dto.OrderDto;
import ru.itis.models.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllUserOrders(Long id);
    Order doOrder(OrderDto orderDto);
    Order getById(Long id);
    Order update(Order order);
}
