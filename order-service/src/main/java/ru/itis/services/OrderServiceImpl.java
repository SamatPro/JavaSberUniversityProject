package ru.itis.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dto.OrderDto;
import ru.itis.enums.ResponseStatus;
import ru.itis.models.*;
import ru.itis.repositories.CartRepository;
import ru.itis.repositories.OrderRepository;
import ru.itis.repositories.OutBoxRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private NextSequenceService nextSequenceService;
    @Autowired
    private OutBoxRepository repository;

    @Override
    public List<Order> getAllUserOrders(Long id) {
        log.debug("Getting all orders of users with ID={}", id);
        return orderRepository
                .findAllByUserId(id);

    }

    @Override
    public Order doOrder(OrderDto orderDto) {
        log.debug("Getting cart of user with ID={}", orderDto.getUserId());
        Cart cart = cartRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("There is no cart with id:" + orderDto.getUserId()));

        log.debug("Inserting new order to cart of user with ID={}", orderDto.getUserId());

        Order order = orderRepository.insert(
                Order.builder()
                        .id(nextSequenceService.getNextSequence("orders"))
                        .status("RECEIVED")
                        .cartItems(cart.getCartItems())
                        .userId(orderDto.getUserId())
                        .customerValidate(new CustomerValidate(ResponseStatus.WAITING))
                        .restaurantValidate(RestaurantValidate.builder()
                                .status(ResponseStatus.WAITING)
                                .build())
                        .date(new Date())
                        .build()
        );
        cart.setCartItems(Collections.emptyList());
        repository.save(new OutBox(nextSequenceService.getNextSequence("outbox"), order.getId()));
        log.debug("Saving cart of user with ID={}", orderDto.getUserId());

        cartRepository.save(cart);
        return order;
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no order with id: " + id));
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

}
