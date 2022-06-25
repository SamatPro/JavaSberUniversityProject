package ru.itis.services;

import ru.itis.models.Dish;
import ru.itis.models.Ingredient;

import java.util.List;

public interface IngredientFromDishService {
    List<Ingredient> getIngredients(Dish dish);
}
