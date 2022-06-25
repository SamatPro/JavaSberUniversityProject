package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dtos.DishDto;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;
import ru.itis.repositories.DishRepository;
import ru.itis.repositories.IngredientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService{

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public Dish add(DishDto dishDto) {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Long id : dishDto.getIngredientsId()) {
            Ingredient ingredient = ingredientRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("The ingredients with ID: " + id + " does not exist"));
            ingredients.add(ingredient);
        }
        Dish dish = Dish.builder()
                .name(dishDto.getName())
                .description(dishDto.getDescription())
                .ingredients(ingredients)
                .build();
        return dishRepository.save(dish);
    }

    @Override
    public Dish get(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The Dish with ID: " + id + " does not exist"));
    }

    @Override
    public Dish update(Long id, DishDto dishDto) {
        Dish dishOld = dishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The Dish with ID: " + id + " does not exist"));
        if (dishDto.getIngredientsId() != null) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (Long ingredientId : dishDto.getIngredientsId()) {
                Ingredient ingredient = ingredientRepository.findById(ingredientId)
                        .orElseThrow(() -> new IllegalArgumentException("The ingredient with ID: " + ingredientId + " does not exist"));
                ingredients.add(ingredient);
            }
            dishOld.setIngredients(ingredients);
        }

        if (dishDto.getName() != null) {
            dishOld.setName(dishDto.getName());
        }

        if (dishDto.getDescription() != null) {
            dishOld.setDescription(dishDto.getDescription());
        }
        return dishRepository.save(dishOld);
    }

    @Override
    public List<Dish> getAll() {
       return dishRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }
}
