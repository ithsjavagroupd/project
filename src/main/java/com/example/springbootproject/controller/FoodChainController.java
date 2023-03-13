package com.example.springbootproject.controller;

import com.example.springbootproject.entity.FoodChain;
import com.example.springbootproject.repository.FoodChainRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foodchain")
public class FoodChainController {

    private final FoodChainRepository repository;

    public FoodChainController(FoodChainRepository foodChainRepository) {
        repository = foodChainRepository;
    }

    @GetMapping("/{id}")
    FoodChain getName(@PathVariable long id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping
    List<FoodChain> getFoodChains()  {
      return repository.findAll();
    }

    @PostMapping
    void addFoodChain(@RequestBody FoodChain foodChain) {
        String name = foodChain.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(foodChain);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        repository.deleteById(id);
    }



}
