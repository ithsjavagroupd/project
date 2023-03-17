package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Store;
import com.example.springbootproject.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreRepository repository;

    public StoreController(StoreRepository storeRepository) {
        repository = storeRepository;
    }

    @GetMapping("/{id}")
    Store getName(@PathVariable long id) {
        return repository.findById(id).orElseThrow();
    }

    @GetMapping
    List<Store> getStores() {
        return repository.findAll();
    }

    @PostMapping
    void addStore(@RequestBody Store store) {
        String name = store.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(store);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store store) {
        Store updateStore = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateStore.setName(store.getName());
        updateStore.setAddress(store.getAddress());
        repository.save(updateStore);

        return ResponseEntity.ok(updateStore);

    }
}
