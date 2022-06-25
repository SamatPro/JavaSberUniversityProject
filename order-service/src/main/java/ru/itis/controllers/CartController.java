package ru.itis.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.CartDto;
import ru.itis.dto.DishDto;
import ru.itis.dto.NewDishDto;
import ru.itis.models.Cart;
import ru.itis.services.CartService;

@RestController
@RequestMapping("/api")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/cart/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        try {
            log.info("Getting cart by ID={}", id);
            return ResponseEntity.ok(cartService.getCartById(id));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cart")
    public ResponseEntity<Cart> addDishToCart(@RequestBody NewDishDto newDishDto) {
        try {
            log.info("Adding dish ID={} to cart ID={}", newDishDto.getDishId(), newDishDto.getUserId());
            return ResponseEntity.ok(cartService.addDishToCart(newDishDto));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cart/deleteItem")
    public ResponseEntity<Cart> deleteItemFromCart(@RequestBody DishDto dishDto) {
        try {
            log.info("Decrease count of dish ID={} from cart ID={}", dishDto.getDishId(), dishDto.getUserId());
            return ResponseEntity.ok(cartService.deleteItemFromCart(dishDto));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cart/deleteDish")
    public ResponseEntity<Cart> deleteDishFromCart(@RequestBody DishDto dishDto) {
        try {
            log.info("Deleting dish ID={} form cart ID={}", dishDto.getDishId(), dishDto.getUserId());
            return ResponseEntity.ok(cartService.deleteDishFromCart(dishDto));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<Cart> deleteCart(@RequestBody CartDto cartDto) {
        try {
            log.info("Deleting cart ID={}", cartDto.getUserId());
            return ResponseEntity.ok(cartService.deleteCart(cartDto));
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
