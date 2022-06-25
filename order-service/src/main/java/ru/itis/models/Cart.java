package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    private Long id;
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new LinkedList<>();
    }

}
