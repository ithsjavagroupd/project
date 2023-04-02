package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Store;
import com.example.springbootproject.projection.StoreName;
import com.example.springbootproject.repository.ChainRepository;
import com.example.springbootproject.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final ChainRepository chainRepository;

    public StoreController(StoreRepository storeRepository, ChainRepository chainRepository) {
        this.storeRepository = storeRepository;
        this.chainRepository = chainRepository;
    }

    @GetMapping("/{id}")
    Store getName(@PathVariable long id) {
        return storeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    List<Store> getStores() {
        try {
            return storeRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto")
    List<StoreName> getAllDtoNames() {
        try {
            return storeRepository.findAllNamesBy();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto/{id}")
    StoreName getOneDtoName(@PathVariable long id) {
        try {
            return storeRepository.findNamesById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    void addStore(@RequestBody Store store) {
        String name = store.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        storeRepository.save(store);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable long id) {
        if(storeRepository.findById(id).isPresent())
        storeRepository.deleteById(id);

        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store store) {
        Store updateStore = storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateStore.setName(store.getName());
        updateStore.setAddress(store.getAddress());
        updateStore.setPhoneNumber(store.getPhoneNumber());
        storeRepository.save(updateStore);

        return ResponseEntity.ok(updateStore);

    }

    @PutMapping("/{storeId}/chains/{chainId}")
    @Transactional
    public void addChainToStore(@PathVariable Long storeId, @PathVariable Long chainId){
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        chainRepository.findById(chainId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        chainRepository.findChainById(chainId).getStores().add(store);
    }
}
