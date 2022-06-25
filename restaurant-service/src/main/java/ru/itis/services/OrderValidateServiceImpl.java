package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dtos.OrderDishDto;
import ru.itis.dtos.OrderResponse;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderValidateServiceImpl implements OrderValidateService {

    @Autowired
    private IngredientFromDishService ingredientFromDishService;

    @Autowired
    private DishService dishService;

    @Override
    public OrderResponse validate(OrderDishDto order) {
        boolean validation = true;
        List<Dish> dishes = order.getDishesId().stream().map(id -> dishService.get(id)).collect(Collectors.toList());
        for (Dish dish :
                dishes) {
            List<Ingredient> ingredients = ingredientFromDishService.getIngredients(dish);
            dish.setIngredients(ingredients);
            for (Ingredient ingredient : ingredients) {
                if (ingredient.isBlock()) {
                    validation = false;
                    break;
                }
            }
        }
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .isValidate(validation)
                .build();
    }
}