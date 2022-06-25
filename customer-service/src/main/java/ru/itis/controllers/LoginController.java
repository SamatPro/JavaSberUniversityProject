package ru.itis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dtos.CustomerDto;
import ru.itis.dtos.LoginDto;
import ru.itis.dtos.TokenDto;
import ru.itis.services.CustomersService;

@RestController
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CustomersService customersService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        try {
            logger.info("Logging in by customer {}", loginDto.getEmail());
            return ResponseEntity.ok(customersService.login(loginDto));
        }catch (SecurityException e){
            logger.warn("Logging in attempt of customer: {}", loginDto.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<TokenDto> register(@RequestBody CustomerDto customerDto) {
        try {
            logger.info("Customer registration {}", customerDto.getEmail());
            return ResponseEntity.ok(customersService.register(customerDto));
        }catch (IllegalArgumentException e){
            logger.warn("Registration attempt of: {}", customerDto.getEmail());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}

