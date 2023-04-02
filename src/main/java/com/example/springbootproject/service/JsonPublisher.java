package com.example.springbootproject.service;

import com.example.springbootproject.dto.ChainDto;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JsonPublisher {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JsonPublisher.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.json.routing.key}")
    private String jsonRoutingKey;

    private RabbitTemplate template;

    public JsonPublisher(RabbitTemplate template) {
        this.template = template;
    }

    public void sendJsonMessage(ChainDto chainDto) {
        logger.info("Json message sent: " + chainDto.toString());
        template.convertAndSend(exchange, jsonRoutingKey, chainDto);
    }
}
