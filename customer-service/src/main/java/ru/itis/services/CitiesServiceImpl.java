package ru.itis.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.dtos.CityDto;
import ru.itis.models.City;
import ru.itis.models.CityStatus;
import ru.itis.repositories.CitiesRepository;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService {

    private static Logger logger = LoggerFactory.getLogger(CitiesServiceImpl.class);

    @Autowired
    private CitiesRepository citiesRepository;

    @Override
    public City add(CityDto cityDto) {

        City city = City.builder()
                .name(cityDto.getName())
                .status(CityStatus.valueOf(cityDto.getStatus()))
                .build();

        logger.debug("Saving a city = {}", cityDto.toString());
        return citiesRepository.save(city);
    }

    @Override
    public City get(Long id) {
        logger.debug("Receiving data from the database");
        return citiesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The city by ID=" + id + " does not exists"));
    }

    @Override
    public City update(Long id, CityDto cityDto) {

        City city = citiesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The city by ID=" + id + " does not exists"));

        if (cityDto.getName() != null){
            city.setName(cityDto.getName());
        }

        if (cityDto.getStatus() != null){
            city.setStatus(CityStatus.valueOf(cityDto.getStatus()));
        }

        logger.debug("Updating a city = {}, {}", id, cityDto);
        return citiesRepository.save(city);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Deleting city by ID={} from database", id);
        citiesRepository.deleteById(id);
    }

    @Override
    public List<City> getAll() {
        List<City> cities = citiesRepository.findAll();
        if (!cities.isEmpty()){
            return cities;
        }
        throw new IllegalStateException("No cities found");
    }
}
