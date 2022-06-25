package ru.itis.services;

import ru.itis.dtos.OrderDishDto;
import ru.itis.dtos.OrderResponse;

public interface OrderValidateService {
    OrderResponse validate(OrderDishDto order);
}
