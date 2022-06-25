package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private Long id;
    private List<CartItem> cartItems;
    private CustomerValidate customerValidate;
    private RestaurantValidate restaurantValidate;
    private String status;
    private Date date;
    private Long userId;

}
