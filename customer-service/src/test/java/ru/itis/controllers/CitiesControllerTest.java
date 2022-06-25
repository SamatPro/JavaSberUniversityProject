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
import ru.itis.dtos.CityDto;
import ru.itis.models.City;
import ru.itis.models.CityStatus;
import ru.itis.services.CitiesService;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class CitiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitiesService citiesService;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void getCityTest(){
        when(citiesService.get(1L)).thenReturn(city());
        mockMvc.perform(get("/cities/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(city().getName()))
                .andExpect(jsonPath("$.status").value(city().getStatus().name()));
    }

    private City city(){
        return City.builder()
                .id(1L)
                .name("Kazan")
                .status(CityStatus.SERVED)
                .build();
    }

    @SneakyThrows
    @Test
    public void getNonexistentCityTest(){
        when(citiesService.get(100L)).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(get("/cities/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    public void addCityTest(){
        when(citiesService.add(cityDto())).thenReturn(city());
        mockMvc.perform(post("/cities")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(city())))
        .andExpect(status().isOk());
    }

    private CityDto cityDto(){
        return CityDto.builder()
                .name("Kazan")
                .status("SERVED")
                .build();
    }

    @SneakyThrows
    @Test
    public void addWithIncorrectStatusTest(){
        when(citiesService.add(incorrectCityDto())).thenThrow(IllegalArgumentException.class);
        mockMvc.perform(post("/cities"))
                .andExpect(status().isBadRequest());
    }

    private CityDto incorrectCityDto(){
        return CityDto.builder()
                .name("Kazan")
                .status("INCORRECT")
                .build();
    }

    @SneakyThrows
    @Test
    public void updateCityTest(){
        when(citiesService.update(1L, updatedCityDto())).thenReturn(updatedCity());
        mockMvc.perform(put("/cities/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updatedCity())))
                .andExpect(status().isOk());
    }

    private CityDto updatedCityDto(){
        return CityDto.builder()
                .name("Barnaul")
                .status("NOT_SERVED")
                .build();
    }

    private City updatedCity(){
        return City.builder()
                .id(1L)
                .name("Barnaul")
                .status(CityStatus.NOT_SERVED)
                .build();
    }

    @SneakyThrows
    @Test
    public void deleteCity(){
        doNothing().when(citiesService).delete(1L);
        mockMvc.perform(delete("/cities/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void deleteNonexistentCityTest(){
        doThrow(EmptyResultDataAccessException.class).when(citiesService).delete(100L);
        mockMvc.perform(delete("/cities/100")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    public void getAllTest(){
        when(citiesService.getAll()).thenReturn(asList(city()));
        mockMvc.perform(get("/cities")
        .content(objectMapper.writeValueAsString(asList(city()))))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void getAllEmptyListTest(){
        when(citiesService.getAll()).thenThrow(IllegalStateException.class);
        mockMvc.perform(get("/cities"))
                .andExpect(status().isServiceUnavailable());
    }
}
