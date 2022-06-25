package ru.itis.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.CartDto;
import ru.itis.dto.DishDto;
import ru.itis.dto.NewDishDto;
import ru.itis.models.Cart;
import ru.itis.models.CartItem;
import ru.itis.models.Dish;
import ru.itis.repositories.CartRepository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CartServiceImpl implements CartService {


    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart getCartById(Long id) {
        log.debug("Getting cart of user with ID={}", id);
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no cart with id:" + id));
    }

    @Override
    public Cart addDishToCart(NewDishDto newDishDto) {
        log.debug("Getting cart of user with ID={}", newDishDto.getUserId());
        Cart cart = cartRepository.findById(Long.valueOf(newDishDto.getUserId()))
                .orElse(Cart.builder()
                        .cartItems(new LinkedList<>())
                        .id(Long.valueOf(newDishDto.getUserId()))
                        .build());
        CartItem potentialCartItem = findPotentialCartItem(cart, newDishDto.getDishId());
        if (potentialCartItem != null) {
            potentialCartItem.increaseCount();
        } else {
            cart.getCartItems().add(
                    CartItem.builder()
                            .count(1)
                            .dish(Dish.builder()
                                    .id(newDishDto.getDishId())
                                    .name(newDishDto.getName())
                                    .description(newDishDto.getDescription())
                                    .build())
                        .build()
            );
        }
        log.debug("Saving cart of user with ID={}", newDishDto.getUserId());
        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteItemFromCart(DishDto dishDto) {
        log.debug("Getting cart of user with ID={}", dishDto.getUserId());

        Cart cart = cartRepository.findById(dishDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("There is no cart with id:" + dishDto.getUserId()));
        CartItem potentialCartItem = findPotentialCartItem(cart, dishDto.getDishId());
        if (potentialCartItem.getCount() > 1) {

            potentialCartItem.decreaseCount();
        } else {
            System.out.println("TEST");
            System.out.println(cart.getCartItems());
            cart.getCartItems().remove(potentialCartItem);
        }

        log.debug("Saving cart of user with ID={}", dishDto.getUserId());

        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteDishFromCart(DishDto dishDto) {
        log.debug("Getting cart of user with ID={}", dishDto.getUserId());

        Cart cart = cartRepository.findById(dishDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("There is no cart with id:" + dishDto.getUserId()));
        CartItem potentialCartItem = findPotentialCartItem(cart, dishDto.getDishId());
        cart.getCartItems().remove(potentialCartItem);
        log.debug("Saving cart of user with ID={}", dishDto.getUserId());

        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteCart(CartDto cartDto) {
        log.debug("Getting cart of user with ID={}", cartDto.getUserId());

        Cart cart = cartRepository.findById(cartDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("There is no cart with id:" + cartDto.getUserId()));
        cart.setCartItems(Collections.emptyList());

        log.debug("Saving cart of user with ID={}", cartDto.getUserId());

        return cartRepository.save(cart);
    }

    private CartItem findPotentialCartItem(Cart cart, Long dishId) {
        AtomicReference<CartItem> sharedPotentialCartItem = new AtomicReference<>();
        cart.getCartItems().forEach(cartItem -> {
            if (cartItem.getDish().getId().equals(dishId)) {
                sharedPotentialCartItem.set(cartItem);
            }
        });
        return sharedPotentialCartItem.get();
    }
}
