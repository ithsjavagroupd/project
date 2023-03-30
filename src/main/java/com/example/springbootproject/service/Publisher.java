package com.example.springbootproject.service;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class Publisher {
    RabbitTemplate template;
    //Logger logger = new Logger();

    String MESSAGE_QUEUE = "message";

    public Publisher(RabbitTemplate template) {
        this.template = template;
    }

    public void publishMessage(String message) {
        template.convertAndSend("my.topic","message.json", message);
    }

    @Bean
    public Queue messageQueue() {
        return new Queue(MESSAGE_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("json");
    }

    @Bean
    public Binding jsonBinding(Queue messageQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(messageQueue)
                .to(exchange)
                .with("message.*");

    }

    @Bean
    @Scheduled(fixedDelay = 3000L)
    public SimpleMessageListenerContainer container (ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MESSAGE_QUEUE);
        container.setMessageListener(message -> System.out.println("Message: " + new String(message.getBody())));
        return container;
    }

}
