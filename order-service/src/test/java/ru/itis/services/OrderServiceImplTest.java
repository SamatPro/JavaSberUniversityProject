package ru.itis.services;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dto.OrderDto;
import ru.itis.models.Cart;
import ru.itis.models.CartItem;
import ru.itis.models.Dish;
import ru.itis.models.Order;
import ru.itis.repositories.CartRepository;
import ru.itis.repositories.OrderRepository;

import java.util.*;

import static java.util.Arrays.asList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class OrderServiceImplTest {

    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @MockBean
    private CartRepository cartRepository;

    @Test
    public void getAllUserOrdersTest() {
        when(orderRepository.findAllByUserId(1L)).thenReturn(asList(order()));
        List<Order> result = orderService.getAllUserOrders(1L);
        assertEquals(result, asList(order()));
        verify(orderRepository, times(1)).findAllByUserId(1L);
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

    @Test
    public void doOrderTest() {
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart()));
        when(orderRepository.insert(Mockito.any(Order.class))).thenReturn(order());
        Order order = orderService.doOrder(orderDto());
        assertEquals(order, order());
        verify(orderRepository, times(1)).insert(Mockito.any(Order.class));
    }


    private Cart cart() {
        return Cart.builder()
                .id(1L)
                .cartItems(Arrays.asList(cartItem()))
                .build();
    }

    private OrderDto orderDto() {
        return OrderDto.builder().userId(1L).build();
    }

    @Test
    public void getByIdTest() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order()));
        Order result = orderService.getById(1L);
        assertEquals(result, order());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void updateTest() {
        when(orderRepository.save(order())).thenReturn(order());
        Order result = orderService.update(order());
        assertEquals(result, order());
        verify(orderRepository, times(1)).save(order());
    }
}
