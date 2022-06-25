package ru.itis.services;

import ru.itis.models.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient add(Ingredient ingredient);
    Ingredient get(Long id);
    Ingredient update(Long id, Ingredient ingredient);
    List<Ingredient> getAll();
    void delete(Long id);
}
