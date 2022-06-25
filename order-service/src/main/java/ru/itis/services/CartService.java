package ru.itis.services;

import ru.itis.dto.CartDto;
import ru.itis.dto.DishDto;
import ru.itis.dto.NewDishDto;
import ru.itis.models.Cart;

public interface CartService {

    Cart getCartById(Long id);
    Cart addDishToCart(NewDishDto newDishDto);
    Cart deleteItemFromCart(DishDto dishDto);
    Cart deleteDishFromCart(DishDto dishDto);
    Cart deleteCart(CartDto cartDto);
}
