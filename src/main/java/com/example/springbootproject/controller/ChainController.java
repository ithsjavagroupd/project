package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.repository.ChainRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/chain")
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
    List<Chain> getChains()  {
      return repository.findAll();
    }

    @PostMapping
    void addChain(@RequestBody Chain foodChain) {
        String name = foodChain.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(foodChain);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Chain> updateChain(@PathVariable Long id, @RequestBody Chain chain) {
        Chain updateChain = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateChain.setName(chain.getName());
        updateChain.setAdress(chain.getAdress());


        repository.save(updateChain);

        return ResponseEntity.ok(updateChain);

    }





}
