package ru.itis.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.dtos.CustomerDto;
import ru.itis.dtos.LoginDto;
import ru.itis.dtos.TokenDto;
import ru.itis.models.City;
import ru.itis.models.CityStatus;
import ru.itis.models.Customer;
import ru.itis.models.CustomerStatus;
import ru.itis.repositories.CitiesRepository;
import ru.itis.repositories.CustomersRepository;
import ru.itis.util.JwtTokenUtil;

import java.util.Optional;

@Service
public class CustomersServiceImpl implements CustomersService {

    private static Logger logger = LoggerFactory.getLogger(CustomersServiceImpl.class);

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Customer add(CustomerDto customerDto) {

        City city = citiesRepository.findById(customerDto.getCityId())
                .orElseThrow(() -> new IllegalArgumentException("The city by ID=" + customerDto.getCityId() + " does not exists"));

        if (!customersRepository.existsCustomerByEmail(customerDto.getEmail())){

            Customer customer = Customer.builder()
                    .lastName(customerDto.getLastName())
                    .firstName(customerDto.getFirstName())
                    .email(customerDto.getEmail())
                    .passwordHash(encoder.encode(customerDto.getPassword()))
                    .city(city)
                    .build();

            logger.debug("Saving a customer {}", customer);
            return customersRepository.save(customer);
        }else {
            throw new IllegalArgumentException("Attempt to add duplicate Email=" + customerDto.getEmail());
        }
    }

    @Override
    public Customer get(Long id) {
        logger.debug("Getting a customer by ID={} from database", id);
        return customersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The customer by ID=" + id + " is not found"));
    }

    @Override
    public Customer update(Long id, CustomerDto customerDto) {

        Customer customer = customersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The customer by ID=" + id + " is not found"));

        if (customerDto.getEmail() == null || !customersRepository.existsCustomerByEmail(customerDto.getEmail())){

            if (customerDto.getCityId() != null){
                City city = citiesRepository.findById(customerDto.getCityId()).orElseThrow(() -> new IllegalArgumentException("The city by ID=" + id + " is not found"));
                customer.setCity(city);
            }

            if (customerDto.getLastName() != null){
                customer.setLastName(customerDto.getLastName());
            }

            if (customerDto.getFirstName() != null){
                customer.setFirstName(customerDto.getFirstName());
            }

            if (customerDto.getEmail() != null){
                customer.setEmail(customerDto.getEmail());
            }

            if (customerDto.getPassword() != null){
                customer.setPasswordHash(encoder.encode(customerDto.getPassword()));
            }

            logger.debug("Updating a customer by ID={}, {}", id, customer);
            return customersRepository.save(customer);
        }else {
            throw new IllegalArgumentException("Attempting to Assign Existing Email");
        }
    }

    @Override
    public void delete(Long id) {
        logger.debug("Deleting a customer by ID={} from database", id);
        customersRepository.deleteById(id);
    }

    @Override
    public TokenDto login(LoginDto loginDto) {
        Optional<Customer> candidate = customersRepository.findCustomerByEmail(loginDto.getEmail());
        if (candidate.isPresent()) {
            Customer customer = candidate.get();
            if (encoder.matches(loginDto.getPassword(), customer.getPasswordHash())) {
                String token = tokenUtil.generateToken(customer);
                return TokenDto.from(token, customer.getId(), customer.getEmail());
            }
        }
        throw new SecurityException("Login attempt failed");
    }

    @Override
    public TokenDto register(CustomerDto customerDto) {
        Customer customer = add(customerDto);
        String token = tokenUtil.generateToken(customer);

        return TokenDto.from(token, customer.getId(), customer.getEmail());
    }

    @Override
    public boolean checkBlockList(Long id) {

        logger.debug("Checking blocklist customer's city, user ID={}", id);

        Customer customer = customersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The customer by ID=" + id + " is not found"));

        if (customer.getCity().getStatus().equals(CityStatus.SERVED)) {
            customer.setStatus(CustomerStatus.CUSTOMER_VALIDATED);
            logger.info("Customer id={} status is validated", id);
            return true;
        }else {
            logger.warn("Customer id={} status is NOT validated", id);
            customer.setStatus(CustomerStatus.CUSTOMER_NOT_VALIDATED);
            return false;
        }
    }

    @Override
    public boolean checkBlockList(String jwt) {
        Long id = tokenUtil.getCustomerIdFromJWT(jwt);
        return checkBlockList(id);
    }

}
