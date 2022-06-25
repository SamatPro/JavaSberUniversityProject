package ru.itis.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.itis.dtos.CustomerDto;
import ru.itis.models.City;
import ru.itis.models.CityStatus;
import ru.itis.models.Customer;
import ru.itis.repositories.CitiesRepository;
import ru.itis.repositories.CustomersRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomersServiceImplTest {

    @Autowired
    private CustomersService customersService;

    @MockBean
    private CustomersRepository customersRepository;

    @MockBean
    private CitiesRepository citiesRepository;

    @Test
    public void getCustomerTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer()));
        Customer result = customersService.get(1L);
        assertEquals(result, customer());
        verify(customersRepository, times(1)).findById(1L);
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
                .firstName("Name")
                .lastName("Sirname")
                .city(city())
                .build();
    }

    @Test
    public void getNonexistentCustomer(){
        assertThrows(IllegalArgumentException.class, () -> customersService.get(101L));
        verify(customersRepository, times(1)).findById(101L);
    }

    @Test
    public void deleteTest(){
        doNothing().when(customersRepository).deleteById(8L);
        customersService.delete(8L);
        verify(customersRepository, times(1)).deleteById(8L);
    }

    @Test
    public void deleteNonexistentTest(){
        doThrow(EmptyResultDataAccessException.class).when(customersRepository).deleteById(100L);
        assertThrows(EmptyResultDataAccessException.class, () -> customersService.delete(100L));
        verify(customersRepository, times(1)).deleteById(100L);
    }

    @Test
    public void addTest(){
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(city()));
        when(customersRepository.existsCustomerByEmail(Mockito.anyString())).thenReturn(false);
        when(customersRepository.save(Mockito.any(Customer.class))).thenReturn(customer());

        Customer result = customersService.add(customerDto());

        assertEquals(result, customer());
        verify(citiesRepository, times(1)).findById(1L);
        verify(customersRepository, times(1)).existsCustomerByEmail(Mockito.anyString());
        verify(customersRepository, times(1)).save(Mockito.any(Customer.class));
    }


    private CustomerDto customerDto(){
        return CustomerDto.builder()
                .firstName("Name")
                .lastName("Sirname")
                .email("example@example.com")
                .password("secret")
                .cityId(1L)
                .build();
    }

    @Test
    public void addWithDuplicateEmailTest(){
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(city()));
        when(customersRepository.existsCustomerByEmail(Mockito.anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> customersService.add(customerDto()));

        verify(citiesRepository, times(1)).findById(1L);
        verify(customersRepository, times(1)).existsCustomerByEmail(Mockito.anyString());
    }

    @Test
    public void addWithNonexistentCityTest(){
        when(citiesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customersService.add(customerDto()));

        verify(citiesRepository, times(1)).findById(1L);
    }

    @Test
    public void updateTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer()));
        when(customersRepository.existsCustomerByEmail(Mockito.anyString())).thenReturn(false);
        when(citiesRepository.findById(1L)).thenReturn(Optional.of(city()));
        when(customersRepository.save(Mockito.any(Customer.class))).thenReturn(updatedCustomer());

        Customer result = customersService.update(1L, updatedCustomerDto());

        assertEquals(result, updatedCustomer());
        verify(customersRepository, times(1)).findById(1L);
        verify(customersRepository, times(1)).existsCustomerByEmail(Mockito.anyString());
        verify(citiesRepository, times(1)).findById(1L);
        verify(customersRepository, times(1)).save(Mockito.any(Customer.class));
    }

    private Customer updatedCustomer(){
        return Customer.builder()
                .id(1L)
                .email("email@mail.ru")
                .firstName("Firstname")
                .lastName("Sirname")
                .city(city())
                .build();
    }

    private CustomerDto updatedCustomerDto(){
        return CustomerDto.builder()
                .firstName("Firstname")
                .email("email@mail.ru")
                .cityId(1L)
                .build();
    }

    @Test
    public void updateNonexistentTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customersService.update(1L, updatedCustomerDto()));

        verify(customersRepository, times(1)).findById(1L);
    }

    @Test
    public void updateWithNonexistentCityTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer()));
        when(customersRepository.existsCustomerByEmail(Mockito.anyString())).thenReturn(false);
        when(citiesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customersService.update(1L, updatedCustomerDto()));

        verify(customersRepository, times(1)).findById(1L);
        verify(customersRepository, times(1)).existsCustomerByEmail(Mockito.anyString());
        verify(citiesRepository, times(1)).findById(1L);
    }

   @Test
    public void updateWithDuplicateEmailTest(){
       when(customersRepository.findById(1L)).thenReturn(Optional.of(customer()));
       when(customersRepository.existsCustomerByEmail(Mockito.anyString())).thenReturn(true);

       assertThrows(IllegalArgumentException.class, () -> customersService.update(1L, updatedCustomerDto()));

       verify(customersRepository, times(1)).findById(1L);
       verify(customersRepository, times(1)).existsCustomerByEmail(Mockito.anyString());
    }

    @Test
    public void checkBlockListWithServedCityTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customer()));

        boolean result = customersService.checkBlockList(1L);
        assertTrue(result);

        verify(customersRepository, times(1)).findById(1L);
    }

    @Test
    public void checkBlockListWithNotServedCityTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.of(customerWithNotServedCity()));

        boolean result = customersService.checkBlockList(1L);
        assertFalse(result);

        verify(customersRepository, times(1)).findById(1L);
    }

    private City notServedCity(){
        return City.builder()
                .id(1L)
                .name("Kazan")
                .status(CityStatus.NOT_SERVED)
                .build();
    }

    private Customer customerWithNotServedCity(){
        return Customer.builder()
                .id(1L)
                .email("example@example.com")
                .firstName("Name")
                .lastName("Sirname")
                .city(notServedCity())
                .build();
    }

    @Test
    public void checkBlockListWithNonexistentCustomerTest(){
        when(customersRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> customersService.checkBlockList(1L));

        verify(customersRepository, times(1)).findById(1L);
    }




}