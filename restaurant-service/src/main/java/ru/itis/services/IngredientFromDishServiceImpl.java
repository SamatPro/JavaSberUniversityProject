package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;
import ru.itis.repositories.DishRepository;

import java.util.List;

@Service
public class IngredientFromDishServiceImpl implements IngredientFromDishService{

    @Autowired
    private DishRepository dishRepository;

    @Override
    public List<Ingredient> getIngredients(Dish dish) {
        Dish dishWithIngredients = dishRepository.findDishByName(dish.getName());
        return dishWithIngredients.getIngredients();
    }
}
