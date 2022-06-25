package ru.itis.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dtos.CityDto;
import ru.itis.models.City;
import ru.itis.models.CityStatus;
import ru.itis.repositories.CitiesRepository;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CitiesServiceImplTest {

    @MockBean
    private CitiesRepository citiesRepository;

    @Autowired
    private CitiesService citiesService;


    @Test
    public void getTest(){
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(city()));
        City result = citiesService.get(1L);
        assertEquals(result, city());
        verify(citiesRepository, times(1)).findById(1L);
    }

    private City city(){
        return City.builder()
                .id(1L)
                .name("Kazan")
                .status(CityStatus.SERVED)
                .build();
    }

    @Test
    public void getNonexistent(){
        assertThrows(IllegalArgumentException.class, () -> citiesService.get(101L));
        verify(citiesRepository, times(1)).findById(101L);
    }

    @Test
    public void addTest(){
        when(citiesRepository.save(Mockito.any(City.class))).thenReturn(newCity());

        City result = citiesService.add(newCityDto());

        assertEquals(result, newCity());
        verify(citiesRepository, times(1)).save(Mockito.any(City.class));
    }

    private City newCity(){
        return City.builder()
                .id(1L)
                .name("New City")
                .status(CityStatus.NOT_SERVED)
                .build();
    }

    private CityDto newCityDto(){
        return CityDto.builder()
                .name("New City")
                .status("NOT_SERVED")
                .build();
    }

    @Test
    public void addWithIncorrectStatus(){
        assertThrows(IllegalArgumentException.class, () -> citiesService.add(newCityDtoWithIncorrectStatus()));
    }

    private CityDto newCityDtoWithIncorrectStatus(){
        return CityDto.builder()
                .name("Test City")
                .status("INCORRECT")
                .build();
    }

    @Test
    public void updateTest(){
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(newCity()));
        when(citiesRepository.save(Mockito.any(City.class))).thenReturn(updatedNewCity());

        City result = citiesService.update(1L, updatedNewCityDto());

        assertEquals(result, updatedNewCity());
        verify(citiesRepository, times(1)).findById(1L);
        verify(citiesRepository, times(1)).save(Mockito.any(City.class));
    }

    private CityDto updatedNewCityDto(){
        return CityDto.builder()
                .name("Updated City")
                .status("SERVED")
                .build();
    }

    private City updatedNewCity(){
        return City.builder()
                .id(1L)
                .status(CityStatus.SERVED)
                .name("Updated City")
                .build();
    }

    @Test
    public void updatePart(){
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(newCity()));
        when(citiesRepository.save(Mockito.any(City.class))).thenReturn(updatedPartiallyCity());

        City result = citiesService.update(1L, updatedPartiallyCityDto());

        assertEquals(result, updatedPartiallyCity());
        verify(citiesRepository, times(1)).findById(1L);
        verify(citiesRepository, times(1)).save(Mockito.any(City.class));
    }

    private CityDto updatedPartiallyCityDto(){
        return CityDto.builder()
                .status("SERVED")
                .build();
    }

    private City updatedPartiallyCity(){
        return City.builder()
                .id(1L)
                .name("New City")
                .status(CityStatus.SERVED)
                .build();
    }

    @Test
    public void updateIncorrectStatusTest(){
        assertThrows(IllegalArgumentException.class, () -> citiesService.update(1L, newCityDtoWithIncorrectStatus()));
    }

    @Test
    public void updateNonexistentTest(){
        when(citiesRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> citiesService.update(100L, updatedPartiallyCityDto()));

        verify(citiesRepository, times(1)).findById(100L);
    }

    @Test
    public void deleteTest(){
        doNothing().when(citiesRepository).deleteById(8L);
        citiesService.delete(8L);
        verify(citiesRepository, times(1)).deleteById(8L);
    }

    @Test
    public void deleteNonexistentTest(){
        doThrow(EmptyResultDataAccessException.class).when(citiesRepository).deleteById(100L);
        assertThrows(EmptyResultDataAccessException.class, () -> citiesService.delete(100L));
        verify(citiesRepository, times(1)).deleteById(100L);
    }

    @Test
    public void getAllTest(){
        when(citiesRepository.findAll()).thenReturn(asList(city()));
        assertEquals(citiesService.getAll(), asList(city()));
        verify(citiesRepository, times(1)).findAll();
    }

    @Test
    public void getAllEmptyTest(){
        when(citiesRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(IllegalStateException.class, () -> citiesService.getAll());
        verify(citiesRepository, times(1)).findAll();
    }

}