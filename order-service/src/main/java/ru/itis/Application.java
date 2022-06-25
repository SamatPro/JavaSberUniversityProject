package ru.itis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.itis.repositories.OrderRepository;
import ru.itis.services.NextSequenceService;


@SpringBootApplication
@EnableAsync
@EnableScheduling
public class Application implements CommandLineRunner {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NextSequenceService nextSequenceService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println(orderRepository.findAllReceived());
    }
}
