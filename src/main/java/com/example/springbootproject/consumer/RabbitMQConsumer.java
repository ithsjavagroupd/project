package com.example.springbootproject.consumer;

import com.example.springbootproject.dto.ChainDto;
import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.mapper.Mapper;
import com.example.springbootproject.repository.ChainRepository;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private final ChainRepository chainRepository;
    Mapper mapper = new Mapper();
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(RabbitMQConsumer.class);

    public RabbitMQConsumer(ChainRepository chainRepository) {
        this.chainRepository = chainRepository;
    }

    @RabbitListener(queues = "${rabbitmq.json.queue.name}")
    public void consumeJsonMessage(ChainDto chainDto) {
        Chain newChain = mapper.map(chainDto);
        String name = newChain.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        chainRepository.save(newChain);
        logger.info("Received json : " + chainDto);
    }


}
