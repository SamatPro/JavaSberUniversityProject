package ru.itis.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.models.Ingredient;
import ru.itis.repositories.IngredientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class IngredientServiceImplTest {

    @Autowired
    private IngredientService ingredientService;

    @MockBean
    private IngredientRepository ingredientRepository;

    private Ingredient ingredient() {
        return Ingredient.builder()
                .id(1L)
                .name("Potato")
                .isBlock(false)
                .build();
    }

    @Test
    void add() {
       Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(ingredient());
       Ingredient result = ingredientService.add(ingredient());
       assertEquals(ingredient(), result);
    }

    @Test
    void get() {
        Mockito.when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient()));
        Ingredient result = ingredientService.get(1L);
        assertEquals(ingredient(), result);
        Mockito.verify(ingredientRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getNoneExist() {
        assertThrows(IllegalArgumentException.class, () -> ingredientService.get(10L));
        Mockito.verify(ingredientRepository, Mockito.times(1)).findById(10L);
    }

    private Ingredient updatedIngredient() {
        return Ingredient.builder()
                .id(1L)
                .name("Potato")
                .isBlock(true)
                .build();
    }

    @Test
    void update() {
        Mockito.when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient()));
        Mockito.when(ingredientRepository.save(Mockito.any(Ingredient.class))).thenReturn(updatedIngredient());
        Ingredient result = ingredientService.update(1L, updatedIngredient());
        assertEquals(updatedIngredient(), result);
        Mockito.verify(ingredientRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(ingredientRepository, Mockito.times(1)).save(Mockito.any(Ingredient.class));
    }

    @Test
    void updateNoneExist() {
        Mockito.when(ingredientRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ingredientService.update(10L, ingredient()));
        Mockito.verify(ingredientRepository, Mockito.times(1)).findById(10L);
    }

    @Test
    void delete() {
        Mockito.doNothing().when(ingredientRepository).deleteById(1L);
        ingredientService.delete(1L);
        Mockito.verify(ingredientRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteNoneExistent() {
        doThrow(EmptyResultDataAccessException.class).when(ingredientRepository).deleteById(10L);
        assertThrows(EmptyResultDataAccessException.class, () -> ingredientService.delete(10L));
        verify(ingredientRepository, times(1)).deleteById(10L);
    }
}