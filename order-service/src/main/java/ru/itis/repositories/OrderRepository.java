package ru.itis.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.itis.models.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {

    List<Order> findAllByUserId(Long id);
    @Query(value = "{'status': 'RECEIVED'}")
    List<Order> findAllReceived();

}
