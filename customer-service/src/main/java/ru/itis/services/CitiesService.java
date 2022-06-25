package ru.itis.services;

import ru.itis.dtos.CityDto;
import ru.itis.models.City;

import java.util.List;

public interface CitiesService {
    City add(CityDto cityDto);
    City get(Long id);
    City update(Long id, CityDto cityDto);
    void delete(Long id);

    List<City> getAll();
}
