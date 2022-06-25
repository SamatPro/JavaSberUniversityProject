package ru.itis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Dish;
import ru.itis.models.Ingredient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DishDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    private List<Long> ingredientsId;

    public static DishDto from(Dish dish) {
        DishDto dishDto = DishDto.builder()
                .id(dish.getId())
                .name(dish.getName())
                .description(dish.getDescription())
                .ingredientsId(new ArrayList<>())
                .build();
        if (dish.getIngredients() != null) {
            for (Ingredient ingredient : dish.getIngredients()) {
                dishDto.getIngredientsId().add(ingredient.getId());
            }
        }
        return dishDto;
    }

    public static List<DishDto> from(List<Dish> dishes) {
        return dishes.stream()
                .map(DishDto::from)
                .collect(Collectors.toList());
    }
}
