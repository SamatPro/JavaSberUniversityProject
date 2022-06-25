package ru.itis.services;

import ru.itis.dtos.CustomerDto;
import ru.itis.dtos.LoginDto;
import ru.itis.dtos.TokenDto;
import ru.itis.models.Customer;

public interface CustomersService {
    Customer add(CustomerDto customerDto);
    Customer get(Long id);
    Customer update(Long id, CustomerDto customerDto);
    void delete(Long id);

    TokenDto login(LoginDto loginDto);
    TokenDto register(CustomerDto customerDto);

    boolean checkBlockList(Long id);
    boolean checkBlockList(String jwt);

}
