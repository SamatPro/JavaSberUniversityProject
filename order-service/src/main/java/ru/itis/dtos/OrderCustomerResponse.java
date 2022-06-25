package ru.itis.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCustomerResponse implements Serializable {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("isValidate")
    private boolean isValidate;
}