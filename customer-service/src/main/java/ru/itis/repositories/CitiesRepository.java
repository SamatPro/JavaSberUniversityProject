package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.City;

@Repository
public interface CitiesRepository extends JpaRepository<City, Long> {
}
