package com.example.springbootproject.controller;

import com.example.springbootproject.dto.ChainDto;
import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.mapper.Mapper;
import com.example.springbootproject.repository.ChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/chains")
public class ChainController {

    private final ChainRepository repository;
 //   @Autowired
    private final Mapper mapper;

    public ChainController(ChainRepository chainRepository, Mapper mapper) {
        repository = chainRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    ChainDto getName(@PathVariable long id) {
        var toConvert = repository.findById(id);
        var converted = mapper.map(toConvert);
        if(converted.)
            return converted. orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    List<ChainDto> getChains()  {
      return mapper.map(repository.findAll());
    }

    @PostMapping
    void addChain(@RequestBody Chain chain) {
        String name = chain.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(chain);
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
        updateChain.setAddress(chain.getAddress());


        repository.save(updateChain);

        return ResponseEntity.ok(updateChain);

    }





}
