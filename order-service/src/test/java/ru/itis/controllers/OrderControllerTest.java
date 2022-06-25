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
import ru.itis.dto.OrderDto;
import ru.itis.models.CartItem;
import ru.itis.models.Dish;
import ru.itis.models.Order;
import ru.itis.services.OrderService;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Ignore
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    private final static String AUTH_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiZW1haWwiOiJzc3MiLCJpYXQiOjE1OTM4NzY5OTIsImV4cCI6MTU5NDIzNjk5Mn0.lClEoRhy_Q6sSP8XlYrxSFU-VuiQHquGyNLiSNewTvBxEAaLudxJeF61vR5Inmaqlz6UMcG9PGiVPMUm-Wzw9Q";

    @SneakyThrows
    @Test
    public void getOrdersTest() {
        when(orderService.getAllUserOrders(1L)).thenReturn(asList(order()));
        mockMvc.perform(get("/api/orders/1")
                        .header("Authorization", AUTH_TOKEN))
                .andExpect(status().isOk());
    }

    private Order order() {
        return Order.builder()
                .status("RECEIVED")
                .userId(1L)
                .cartItems(new ArrayList<>(asList(
                        cartItem()
                )))
                .build();
    }

    private CartItem cartItem() {
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

    @SneakyThrows
    @Test
        public void doOrderTest() {
        when(orderService.doOrder(orderDto())).thenReturn(order());
        mockMvc.perform(post("/api/order")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", AUTH_TOKEN)
                .content(objectMapper.writeValueAsBytes(orderDto())))
                .andExpect(status().isOk());
    }


    private OrderDto orderDto() {
        return OrderDto.builder().userId(1L).build();
    }
}
