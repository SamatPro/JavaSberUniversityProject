package ru.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dto.CartDto;
import ru.itis.dto.DishDto;
import ru.itis.dto.NewDishDto;
import ru.itis.models.Cart;
import ru.itis.models.CartItem;
import ru.itis.models.Dish;
import ru.itis.services.CartService;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Ignore
public class CartControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService cartService;
    @Autowired
    private ObjectMapper objectMapper;

    private final static String AUTH_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJzc3MiLCJpYXQiOjE1OTM4NzY5OTIsImV4cCI6MTU5NDIzNjk5Mn0.lClEoRhy_Q6sSP8XlYrxSFU-VuiQHquGyNLiSNewTvBxEAaLudxJeF61vR5Inmaqlz6UMcG9PGiVPMUm-Wzw9Q";

    @SneakyThrows
    @Test
    public void getCartTest() {
        when(cartService.getCartById(1L)).thenReturn(cart());
        System.out.println(cart());
        this.mockMvc.perform(get("/api/cart/1")
                .content(objectMapper.writeValueAsString(cart().getCartItems()))
                .header("Authorization", AUTH_TOKEN)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cart().getId()));
    }

    private Cart cart() {
        return Cart.builder()
                .id(1L)
                .cartItems((Arrays.asList(cartItem1())))
                .build();
    }

    private CartItem cartItem1() {
        return CartItem.builder()
                .count(1)
                .dish(dish())
                .build();
    }

    private CartItem cartItem2() {
        return CartItem.builder()
                .count(1)
                .dish(dish2())
                .build();
    }

    private Dish dish() {
        return Dish.builder()
                .id(1L)
                .name("Лапша")
                .description("Вкусная лапша")
                .build();
    }

    private Dish dish2() {
        return Dish.builder()
                .id(1L)
                .name("Каша")
                .description("Вкусная каша")
                .build();
    }

    @SneakyThrows
    @Test
    public void addDishToCartTest() {
        when(cartService.addDishToCart(newDishDto())).thenReturn(cart());
        mockMvc.perform(post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dishDto()))
                .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());

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

    @SneakyThrows
    @Test
    public void deleteItemFromCartTest() {
        when(cartService.deleteItemFromCart(dishDto())).thenReturn(emptyCart());
        mockMvc.perform(post("/api/cart/deleteItem")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dishDto()))
                .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());

    }

    private Cart emptyCart() {
        return Cart.builder()
                .id(1L)
                .cartItems(Collections.emptyList())
                .build();
    }

    @SneakyThrows
    @Test
    public void deleteDishFromCartTest() {
        when(cartService.deleteDishFromCart(dishDto())).thenReturn(emptyCart());
        mockMvc.perform(post("/api/cart/deleteDish")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dishDto()))
                .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());

    }

    @SneakyThrows
    @Test
    public void deleteCartTest() {
        when(cartService.deleteCart(cartDto())).thenReturn(emptyCart());
        mockMvc.perform(post("/api/cart/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dishDto()))
                .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());

    }

    private CartDto cartDto() {
        return CartDto.builder().userId(1L).build();
    }
}
