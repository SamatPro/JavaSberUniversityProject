package ru.itis.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.dtos.CityDto;
import ru.itis.models.City;
import ru.itis.services.CitiesService;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CitiesController {

    private static Logger logger = LoggerFactory.getLogger(CitiesController.class);

    @Autowired
    private CitiesService citiesService;

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable("id") Long id){
        try{
            logger.info("Getting a city by ID={}", id);
            return ResponseEntity.ok(citiesService.get(id));
        }catch (IllegalArgumentException e){
            logger.warn(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody CityDto cityDto){
        try {
            logger.info("Adding a new city {}", cityDto.toString());
            return ResponseEntity.ok(citiesService.add(cityDto));
        }catch (IllegalArgumentException e){
            logger.warn("City status is not correct: {}", cityDto.getStatus());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable("id") Long id,
                                           @RequestBody CityDto cityDto){
        try{
            logger.info("Updating a city by ID={}, {}", id, cityDto.toString());
            return ResponseEntity.ok(citiesService.update(id, cityDto));
        }catch (IllegalArgumentException e){
            logger.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") Long id){
        try {
            citiesService.delete(id);
            logger.info("The city by ID={} is deleted", id);
            return ResponseEntity.ok().build();
        }catch (EmptyResultDataAccessException e){
            logger.warn("The deleted city by ID={} does not exist", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<City>> getCities(){
        try {
            logger.info("Getting cities list");
            return ResponseEntity.ok(citiesService.getAll());
        }catch (IllegalStateException e){
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
