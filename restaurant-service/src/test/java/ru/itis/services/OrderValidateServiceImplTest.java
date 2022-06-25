package ru.itis.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dtos.OrderDishDto;
import ru.itis.dtos.OrderResponse;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderValidateServiceImplTest {

    @Autowired
    private OrderValidateService orderValidateService;

    @MockBean
    private IngredientFromDishService ingredientFromDishService;

    private OrderDishDto orderDishDtoWithNonBlockedIngredients() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish1WithoutIngredients());
        dishes.add(dish2WithoutIngredients());
        return OrderDishDto.builder()
                .orderId(1L)
                .dishesId(dishes.stream()
                        .map(Dish::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    Dish dish1WithIngredients() {
        Ingredient ingredientForDish1 = Ingredient.builder().id(1L).name("testIngredient").isBlock(false).build();
        Ingredient ingredientForDish1_2 = Ingredient.builder().id(2L).name("testIngredient2").isBlock(false).build();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredientForDish1);
        ingredients.add(ingredientForDish1_2);
        return Dish.builder()
                .id(1L)
                .name("test1")
                .description("test1")
                .ingredients(ingredients)
                .build();
    }

    Dish dish2WithIngredients() {
        Ingredient ingredientForDish2 = Ingredient.builder().id(3L).name("testIngredient3").isBlock(false).build();
        Ingredient ingredientForDish2_2 = Ingredient.builder().id(4L).name("testIngredient4").isBlock(false).build();
        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(ingredientForDish2);
        ingredients1.add(ingredientForDish2_2);
        return Dish.builder()
                .id(2L)
                .name("test2")
                .description("test2")
                .ingredients(ingredients1)
                .build();
    }

    Dish dish1WithoutIngredients() {
        return Dish.builder()
                .id(1L)
                .name("test1")
                .description("test1")
                .build();
    }

    Dish dish2WithoutIngredients() {
        return Dish.builder()
                .id(2L)
                .name("test2")
                .description("test2")
                .build();
    }

    OrderResponse orderResponseWithNonBlockedDishes() {
        return OrderResponse.builder()
                .isValidate(true)
                .orderId(1L)
                .build();
    }

    @Test
    void validateWithNonBlockedIngredients() {
        Mockito.when(ingredientFromDishService.getIngredients(dish1WithoutIngredients())).thenReturn(dish1WithIngredients().getIngredients());
        Mockito.when(ingredientFromDishService.getIngredients(dish2WithoutIngredients())).thenReturn(dish2WithIngredients().getIngredients());
        OrderResponse result = orderValidateService.validate(orderDishDtoWithNonBlockedIngredients());
        assertEquals(orderResponseWithNonBlockedDishes(), result);
    }

    Dish dish1WithBlockedIngredients() {
        Ingredient ingredientForDish1 = Ingredient.builder().id(1L).name("testIngredient").isBlock(true).build();
        Ingredient ingredientForDish1_2 = Ingredient.builder().id(2L).name("testIngredient2").isBlock(false).build();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredientForDish1);
        ingredients.add(ingredientForDish1_2);
        return Dish.builder()
                .id(1L)
                .name("test1")
                .description("test1")
                .ingredients(ingredients)
                .build();
    }

    Dish dish2WithBlockedIngredients() {
        Ingredient ingredientForDish2 = Ingredient.builder().id(3L).name("testIngredient3").isBlock(true).build();
        Ingredient ingredientForDish2_2 = Ingredient.builder().id(4L).name("testIngredient4").isBlock(true).build();
        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(ingredientForDish2);
        ingredients1.add(ingredientForDish2_2);
        return Dish.builder()
                .id(2L)
                .name("test2")
                .description("test2")
                .ingredients(ingredients1)
                .build();
    }

    OrderResponse orderResponseWithBlockedDishes() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(dish1WithBlockedIngredients());
        dishes.add(dish2WithBlockedIngredients());
        return OrderResponse.builder()
                .isValidate(false)
                .orderId(1L)
                .build();
    }

//    @Test
//    void validateWithBlockedIngredients() {
//        Mockito.when(ingredientFromDishService.getIngredients(dish1WithoutIngredients())).thenReturn(dish1WithBlockedIngredients().getIngredients());
//        Mockito.when(ingredientFromDishService.getIngredients(dish2WithoutIngredients())).thenReturn(dish2WithBlockedIngredients().getIngredients());
//        OrderResponse result = orderValidateService.validate(orderDishDtoWithNonBlockedIngredients());
//        assertEquals(orderResponseWithBlockedDishes(), result);
//    }
}