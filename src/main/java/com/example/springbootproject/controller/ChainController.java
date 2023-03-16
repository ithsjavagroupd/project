package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.repository.ChainRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foodchain")
public class ChainController {

    private final ChainRepository repository;

    public ChainController(ChainRepository chainRepository) {
        repository = chainRepository;
    }

    @GetMapping("/{id}")
    Chain getName(@PathVariable long id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping
    List<Chain> getFoodChains()  {
      return repository.findAll();
    }

    @PostMapping
    void addFoodChain(@RequestBody Chain foodChain) {
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
