package ru.itis.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.OrderDto;
import ru.itis.models.Order;
import ru.itis.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Order> doOrder(@RequestBody OrderDto orderDto) {
        try {
            log.info("Making order of user with ID={}", orderDto.getUserId());
           return ResponseEntity.ok(orderService.doOrder(orderDto));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long id) {
        try {
            log.info("Getting orders of user with ID={}", id);
            return ResponseEntity.ok(orderService.getAllUserOrders(id));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
