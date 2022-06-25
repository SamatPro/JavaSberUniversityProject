package ru.itis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dtos.CustomerDto;
import ru.itis.models.Customer;
import ru.itis.services.CustomersService;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    private static Logger logger = LoggerFactory.getLogger(CustomersController.class);


    @Autowired
    private CustomersService customersService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id){
        try{
            logger.info("Getting a customer by ID={}", id);
            return ResponseEntity.ok(customersService.get(id));
        }catch (IllegalArgumentException e){
            logger.warn("The customer by ID={} is not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDto customerDto){
        try {
            logger.info("Adding a new customer {}", customerDto.toString());
            return ResponseEntity.status(201)
                    .body(customersService.add(customerDto));
        }catch (IllegalArgumentException e){
            logger.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id,
                                                   @RequestBody CustomerDto customerDto){
        try{
            logger.info("Updating a customer by ID={}, {}", id, customerDto);
            return ResponseEntity.ok(customersService.update(id, customerDto));
        }catch (IllegalArgumentException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        try {
            customersService.delete(id);
            logger.info("The customer by ID={} is deleted", id);
            return ResponseEntity.ok().build();
        }catch (EmptyResultDataAccessException e){
            logger.warn("The deleted customer by ID={} does not exist", id);
            return ResponseEntity.notFound().build();
        }
    }
}
