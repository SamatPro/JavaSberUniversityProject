package ru.itis.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.models.OutBox;


public interface OutBoxRepository extends MongoRepository<OutBox, Long> {
}