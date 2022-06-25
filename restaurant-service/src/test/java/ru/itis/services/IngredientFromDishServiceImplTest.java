package ru.itis.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;
import ru.itis.repositories.DishRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class IngredientFromDishServiceImplTest {

    @Autowired
    private IngredientFromDishService ingredientFromDishService;

    @MockBean
    private DishRepository dishRepository;

    @Test
    void getIngredients() {
        Dish dishWithOutIngredients = Dish.builder().id(1L).name("test").description("test").build();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder().id(1L).name("Potato").isBlock(true).build());
        Dish dishWithIngredeients = Dish.builder().id(1L).name("test").description("test").ingredients(ingredients).build();
        Mockito.when(dishRepository.findDishByName(dishWithOutIngredients.getName())).thenReturn(dishWithIngredeients);
        List<Ingredient> result = ingredientFromDishService.getIngredients(dishWithOutIngredients);
        assertEquals(dishWithIngredeients.getIngredients(), result);
        Mockito.verify(dishRepository, Mockito.times(1)).findDishByName(dishWithOutIngredients.getName());
    }
}