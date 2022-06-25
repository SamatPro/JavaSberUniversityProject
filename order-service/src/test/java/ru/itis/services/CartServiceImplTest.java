package ru.itis.services;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dto.CartDto;
import ru.itis.dto.DishDto;
import ru.itis.dto.NewDishDto;
import ru.itis.models.Cart;
import ru.itis.models.CartItem;
import ru.itis.models.Dish;
import ru.itis.repositories.CartRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class CartServiceImplTest {

    @MockBean
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void getCartByIdTest() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart()));
        Cart result = cartService.getCartById(1L);
        System.out.println(result);
        System.out.println(cart());
        assertEquals(result, cart());
        verify(cartRepository, times(1)).findById(1L);
    }

    private Cart cart() {
        return Cart.builder()
                .id(1L)
                .cartItems(Arrays.asList(cartItem1()))
                .build();
    }

    private CartItem cartItem1() {
        return CartItem.builder()
                .count(1)
                .dish(dish())
                .build();
    }

    private Dish dish() {
        return Dish.builder()
                .id(1L)
                .name("Лапша")
                .description("Вкусная лапша")
                .build();
    }

    @Test
    public void addDishToCartTest() {
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(newCart());
        Cart result = cartService.addDishToCart(newDishDto());
        assertEquals(result,newCart());
        verify(cartRepository, times(1)).save(newCart());
    }

    private DishDto dishDto() {
        return DishDto.builder()
                .dishId(1L)
                .userId(1L)
                .build();
    }

    private NewDishDto newDishDto() {
        return NewDishDto.builder()
                .dishId(1L)
                .userId("1")
                .name("Лапша")
                .description("Вкусно")
                .build();
    }


    private Cart newCart() {
        return Cart.builder()
                .id(1L)
                .cartItems(Arrays.asList(CartItem.builder()
                .dish(Dish.builder().id(1L).build())
                .build()))
                .build();
    }

    @Test
    public void deleteItemFromCartTest() {
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(emptyCart());
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cartToDeleteItem()));
        Cart result = cartService.deleteItemFromCart(dishDto());
        assertEquals(result,emptyCart());
        verify(cartRepository, times(1)).save(emptyCart());
    }

    @Test
    public void deleteDishFromCartTest() {
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(emptyCart());
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cartToDeleteItem()));
        Cart result = cartService.deleteDishFromCart(dishDto());
        System.out.println(result);
        assertEquals(result,emptyCart());
        verify(cartRepository, times(1)).save(emptyCart());
    }

    private Cart cartToDeleteItem() {
        return Cart.builder()
                .id(1L)
                .cartItems(new ArrayList<>(Arrays.asList(CartItem.builder()
                        .count(1)
                        .dish(Dish.builder().id(1L).build())
                        .build())))
                .build();
    }
    @Test
    public void deleteCartTest() {
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(emptyCart());
        when(cartRepository.findById(1L)).thenReturn(Optional.of(emptyCart()));
        Cart result = cartService.deleteCart(cartDto());
        System.out.println(result);
        assertEquals(result,emptyCart());
        verify(cartRepository, times(1)).save(emptyCart());
    }

    private Cart emptyCart() {
        return Cart.builder()
                .id(1L)
                .cartItems(Collections.emptyList())
                .build();
    }

    private CartDto cartDto() {
        return CartDto.builder().userId(1L).build();
    }

}
