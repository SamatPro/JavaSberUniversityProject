package ru.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dtos.DishDto;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;
import ru.itis.services.DishService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class DishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DishService dishService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    void addDish() {
        when(dishService.add(dishDto())).thenReturn(dish());
        try {
            mockMvc.perform(post("/dish")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(dish())))
                    .andExpect(status().is(201));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void getDish() {
        when(dishService.get(1L)).thenReturn(dish());
        try {
            mockMvc.perform(get("/dish/1")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(dish().getName()))
                    .andExpect(jsonPath("$.description").value(dish().getDescription()));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
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
    void updateDish() {
        when(dishService.update(1L, updatedDishDto())).thenReturn(updatedDish());
        try {
            mockMvc.perform(put("/dish/1")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(updatedDish())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void deleteDish() {
        doNothing().when(dishService).delete(1L);
        try {
            mockMvc.perform(delete("/dish/1")).andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void getNoneExistDish() {
        when(dishService.get(100L)).thenThrow(IllegalArgumentException.class);
        try {
            mockMvc.perform(get("/dish/100")).andDo(print())
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private DishDto incorrectDishDto() {
        List<Long> longList = new ArrayList<>();
        longList.add(1000L);
        return DishDto.builder()
                .name("test")
                .description("test")
                .ingredientsId(longList)
                .build();
    }

    @Test
    void addWithIncorrectIngredient() {
        when(dishService.add(incorrectDishDto())).thenThrow(IllegalArgumentException.class);
        try {
            mockMvc.perform(post("/dish"))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}