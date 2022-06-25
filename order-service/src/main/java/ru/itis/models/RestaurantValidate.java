package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.enums.ResponseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantValidate {
    private ResponseStatus status;
}