package ru.itis.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {

    public static final String DIRECT_EXCHANGE_NAME = "response-cst-exchange";

    public static final String CUSTOMER_ROUTING_KEY = "cst";

    public static final String CUSTOMER_QUEUE_NAME = "req_cst";

    private static final String REPLY_CUSTOMER_QUEUE_NAME = "rep_cst";

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue customerReplyQueue() {
        return new Queue(REPLY_CUSTOMER_QUEUE_NAME, false);
    }

    @Bean
    Queue customerQueue() {
        return new Queue(CUSTOMER_QUEUE_NAME, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    Binding bindingCustomer(Queue customerReplyQueue, DirectExchange exchange) {
        return BindingBuilder.bind(customerReplyQueue).to(exchange).with(CUSTOMER_ROUTING_KEY);
    }
}