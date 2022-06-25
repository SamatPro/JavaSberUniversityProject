package ru.itis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDishDto implements Serializable {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("dishesId")
    private List<Long> dishesId;
}