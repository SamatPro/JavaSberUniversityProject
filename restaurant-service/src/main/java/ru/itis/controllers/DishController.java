package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dtos.DishDto;
import ru.itis.models.Dish;
import ru.itis.services.DishService;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody DishDto dishDto) {
        try {
            return ResponseEntity.status(201)
                    .body(dishService.add(dishDto));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDish(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(dishService.get(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes() {
        return ResponseEntity.ok(dishService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable("id") Long id, @RequestBody DishDto dishDto) {
        try {
            return ResponseEntity.ok(dishService.update(id, dishDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable("id") Long id) {
            dishService.delete(id);
            return ResponseEntity.ok().build();
    }
}
