package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findDishByName(String name);
}
