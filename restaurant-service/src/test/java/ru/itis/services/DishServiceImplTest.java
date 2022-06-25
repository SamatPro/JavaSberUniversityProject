package ru.itis.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dtos.DishDto;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;
import ru.itis.repositories.DishRepository;
import ru.itis.repositories.IngredientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DishServiceImplTest {

    @Autowired
    private DishService dishService;

    @MockBean
    private DishRepository dishRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    private DishDto dishDto() {
        List<Long> ingredients = new ArrayList<>();
        ingredients.add(ingredient().getId());
        return DishDto.builder()
                .id(1L)
                .name("Beef with cheese")
                .description("Test")
                .ingredientsId(ingredients)
                .build();
    }

    private Ingredient ingredient() {
        return Ingredient.builder()
                .id(1L)
                .name("Potato")
                .isBlock(false)
                .build();
    }

    private Dish dish() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient());
        return Dish.builder()
                .id(1L)
                .name("Beef with cheese")
                .description("Test")
                .ingredients(ingredients)
                .build();
    }

    @Test
    void add() {
        Mockito.when(dishRepository.save(Mockito.any(Dish.class))).thenReturn(dish());
        Mockito.when(ingredientRepository.findById(dishDto().getIngredientsId().get(0))).thenReturn(Optional.of(ingredient()));
        Dish result = dishService.add(dishDto());
        assertEquals(dish(), result);
    }

    @Test
    void get() {
        Mockito.when(dishRepository.findById(1L)).thenReturn(Optional.of(dish()));
        Dish result = dishService.get(1L);
        assertEquals(dish(), result);
        Mockito.verify(dishRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getNoneExist() {
        assertThrows(IllegalArgumentException.class, () -> dishService.get(10L));
        Mockito.verify(dishRepository, Mockito.times(1)).findById(10L);
    }

    private DishDto updatedDishDto() {
        return DishDto.builder()
                .id(1L)
                .name("test")
                .description("Test1")
                .ingredientsId(new ArrayList<>())
                .build();
    }

    private Dish updatedDish() {
        return Dish.builder()
                .id(1L)
                .name("test")
                .description("Test1")
                .ingredients(new ArrayList<>())
                .build();
    }

    @Test
    void update() {
        Mockito.when(dishRepository.findById(1L)).thenReturn(Optional.of(dish()));
        Mockito.when(dishRepository.save(Mockito.any(Dish.class))).thenReturn(updatedDish());
        Dish result = dishService.update(1L, updatedDishDto());
        assertEquals(updatedDish(), result);
        Mockito.verify(dishRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(dishRepository, Mockito.times(1)).save(Mockito.any(Dish.class));
    }

    @Test
    void updateNoneExist() {
        Mockito.when(dishRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> dishService.update(10L, dishDto()));
        Mockito.verify(dishRepository, Mockito.times(1)).findById(10L);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(dishRepository).deleteById(1L);
        dishService.delete(1L);
        Mockito.verify(dishRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteNoneExistent() {
        doThrow(EmptyResultDataAccessException.class).when(dishRepository).deleteById(10L);
        assertThrows(EmptyResultDataAccessException.class, () -> dishService.delete(10L));
        verify(dishRepository, times(1)).deleteById(10L);
    }
}