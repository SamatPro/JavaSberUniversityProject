package ru.itis.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long id;

    private String lastName;
    private String firstName;
    private String email;
    private String password;

    private Long cityId;
    private String status;

}
