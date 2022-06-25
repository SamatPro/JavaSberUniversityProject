package ru.itis.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
    private String value;
    private Long customerId;
    private String email;

    public static TokenDto from(String token, Long id, String email) {
        return TokenDto.builder()
                .customerId(id)
                .value(token)
                .email(email)
                .build();
    }
}
