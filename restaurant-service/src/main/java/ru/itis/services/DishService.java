package ru.itis.services;

import ru.itis.dtos.DishDto;
import ru.itis.models.Dish;

import java.util.List;

public interface DishService {
    Dish add(DishDto dishDto);
    Dish get(Long id);
    Dish update(Long id, DishDto dishDto);
    void delete(Long id);
    List<Dish> getAll();
}
