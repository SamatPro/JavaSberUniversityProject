package ru.itis.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.models.Cart;

public interface CartRepository extends MongoRepository<Cart, Long> {

}
