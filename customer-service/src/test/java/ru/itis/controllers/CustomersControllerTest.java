package ru.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.itis.dtos.CustomerDto;
import ru.itis.models.City;
import ru.itis.models.CityStatus;
import ru.itis.models.Customer;
import ru.itis.services.CustomersService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CustomersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomersService customersService;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void getCustomerTest(){
        when(customersService.get(1L)).thenReturn(customer());
        mockMvc.perform(get("/customers/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(customer().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(customer().getLastName()))
                .andExpect(jsonPath("$.email").value(customer().getEmail()))
                .andExpect(jsonPath("$.city").value(city()));
    }

    private City city(){
        return City.builder()
                .id(1L)
                .name("Kazan")
                .status(CityStatus.SERVED)
                .build();
    }

    private Customer customer(){
        return Customer.builder()
                .id(1L)
                .email("example@example.com")
                .firstName("Джошуа")
                .lastName("Блох")
                .city(city())
                .build();
    }

    @SneakyThrows
    @Test
    public void getNonexistentCustomerTest(){
        when(customersService.get(100L)).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(get("/customers/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    public void addCustomerTest(){
        when(customersService.add(customerDto())).thenReturn(customer());
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(customer())))
                .andExpect(status().isCreated());
    }

    private CustomerDto customerDto(){
        return CustomerDto.builder()
                .firstName("Джошуа")
                .lastName("Блох")
                .email("example@example.com")
                .cityId(1L)
                .build();
    }

    @SneakyThrows
    @Test
    public void addCustomerWithNonexistentCityTest(){
        when(customersService.add(customerDto())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(post("/customers")).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    public void updateCustomerTest(){
        when(customersService.update(1L, updatedCustomerDto())).thenReturn(updatedCustomer());
        mockMvc.perform(put("/customers/1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(updatedCustomer())))
                .andExpect(status().isOk());

    }

    private CustomerDto updatedCustomerDto(){
        return CustomerDto.builder()
                .firstName("Джефф")
                .lastName("Безос")
                .build();
    }

    private Customer updatedCustomer(){
        return Customer.builder()
                .id(1L)
                .email("example@example.com")
                .firstName("Джефф")
                .lastName("Безос")
                .city(city())
                .build();
    }

    @SneakyThrows
    @Test
    public void deleteCustomer(){
        doNothing().when(customersService).delete(1L);
        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void deleteNonexistentCustomerTest(){
        doThrow(EmptyResultDataAccessException.class).when(customersService).delete(100L);
        mockMvc.perform(delete("/customers/100")).andDo(print())
                .andExpect(status().isNotFound());
    }


}
