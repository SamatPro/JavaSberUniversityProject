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
import ru.itis.models.Ingredient;
import ru.itis.services.IngredientService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Ingredient ingredient() {
        return Ingredient.builder()
                .id(1L)
                .name("Potato")
                .isBlock(false)
                .build();
    }

    @Test
    void addIngredient() {
        when(ingredientService.add(ingredient())).thenReturn(ingredient());
        try {
            mockMvc.perform(post("/ingredient")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(ingredient())))
                    .andExpect(status().is(200));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void getIngredient() {
        when(ingredientService.get(1L)).thenReturn(ingredient());
        try {
            mockMvc.perform(get("/ingredient/1")).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value(ingredient().getName()))
                    .andExpect(jsonPath("$.block").value(ingredient().isBlock()));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void updateIngredient() {
        when(ingredientService.update(1L, ingredient())).thenReturn(ingredient());
        try {
            mockMvc.perform(put("/ingredient/1")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(ingredient())))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void deleteIngredient() {
        doNothing().when(ingredientService).delete(1L);
        try {
            mockMvc.perform(delete("/ingredient/1")).andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Test
    void getNoneExistIngredient() {
        when(ingredientService.get(100L)).thenThrow(IllegalArgumentException.class);
        try {
            mockMvc.perform(get("/ingredient/100")).andDo(print())
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}