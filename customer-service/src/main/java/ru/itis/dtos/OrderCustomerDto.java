package ru.itis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCustomerDto implements Serializable {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("customerId")
    private Long userId;
}