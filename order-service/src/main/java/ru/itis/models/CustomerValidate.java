package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.enums.ResponseStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerValidate {

    private ResponseStatus status;
}