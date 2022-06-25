package ru.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dtos.CustomerDto;
import ru.itis.dtos.LoginDto;
import ru.itis.dtos.TokenDto;
import ru.itis.services.CustomersService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomersService customersService;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void loginTest(){
        when(customersService.login(loginDto())).thenReturn(tokenDto());
        mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(tokenDto())))
                .andExpect(status().isOk());
    }

    private LoginDto loginDto(){
        return LoginDto.builder()
                .email("example@example.com")
                .password("secret")
                .build();
    }

    private TokenDto tokenDto(){
        return TokenDto.from("tokenn", 1L, "example@example.com");
    }

    @SneakyThrows
    @Test
    public void loginFailureTest(){
        when(customersService.login(loginDto())).thenThrow(SecurityException.class);
        mockMvc.perform(post("/login"))
                .andExpect(status().is4xxClientError());
    }

    @SneakyThrows
    @Test
    public void registerTest(){
        when(customersService.register(customerDto())).thenReturn(tokenDto());
        mockMvc.perform(post("/registration")
            .content(objectMapper.writeValueAsString(tokenDto()))
            .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    private CustomerDto customerDto(){
        return CustomerDto.builder()
                .firstName("Роберт")
                .lastName("Мартин")
                .email("martin@mail.ru")
                .cityId(1L)
                .password("secret")
                .build();
    }

}
