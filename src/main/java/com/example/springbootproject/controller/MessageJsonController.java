package com.example.springbootproject.controller;

import com.example.springbootproject.dto.ChainDto;
import com.example.springbootproject.service.JsonPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageJsonController {

    private JsonPublisher publisher;

    public MessageJsonController(JsonPublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/json")
    public void postJsonMessage(@RequestBody ChainDto chainDto){
        publisher.sendJsonMessage(chainDto);
    }
}
