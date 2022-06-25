package ru.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itis.dtos.OrderCustomerDto;
import ru.itis.dtos.OrderDishDto;
import ru.itis.models.Order;
import ru.itis.models.OutBox;
import ru.itis.repositories.OrderRepository;
import ru.itis.repositories.OutBoxRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageSendSchedulerService {

    @Autowired
    OutBoxRepository outBoxRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MessageBrokerSender sender;

    @Scheduled(cron = "0 0/1 * * * ?")
    private void sendAndDeleteAllMessages() {
        List<OutBox> orders = outBoxRepository.findAll();
        for (OutBox outboxOrder :
                orders) {
            Order order = orderRepository.findById(outboxOrder.getOrderId()).orElseThrow(IllegalArgumentException::new);
            sender.sendMessageToCustomer(new OrderCustomerDto(order.getId(), order.getUserId()));
            sender.sendMessageToRestaurant(
                    new OrderDishDto(order.getId(),
                            order.getCartItems().stream()
                                    .map(s -> s.getDish().getId())
                                    .collect(Collectors.toList())));
            outBoxRepository.delete(outboxOrder);
        }
    }
}