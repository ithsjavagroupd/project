package com.example.springbootproject.service;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Publisher {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Publisher.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private RabbitTemplate template;

    public Publisher(RabbitTemplate template) {
        this.template = template;
    }

    public void publishMessage(String message) {
        logger.info("Sending message to queue: " + queue);
        template.convertAndSend(exchange, routingKey, message);
    }
}
