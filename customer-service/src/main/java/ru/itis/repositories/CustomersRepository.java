package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.Customer;

import java.util.Optional;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, Long> {
    boolean existsCustomerByEmail(String email);
    Optional<Customer> findCustomerByEmail(String email);
}
