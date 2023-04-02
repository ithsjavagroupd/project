package com.example.springbootproject.controller;

import com.example.springbootproject.service.Publisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final Publisher publisher;

    public MessageController(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/get")
    public void getMessage(@RequestParam String id){
        publisher.publishMessage(id);
    }

//    @PostMapping
//    public void postMessage(@RequestBody String message){
//        publisher.publishMessage(message);
//    }
}
