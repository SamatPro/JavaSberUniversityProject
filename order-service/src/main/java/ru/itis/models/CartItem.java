package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    private Integer count = 1;
    private Dish dish;

    public void increaseCount() {
        count++;
    }

    public void decreaseCount() {
        count--;
    }
}
