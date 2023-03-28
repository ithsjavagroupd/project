package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.projection.ChainName;
import com.example.springbootproject.projection.StoreName;
import com.example.springbootproject.repository.ChainRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/chains")
public class ChainController {

    private final ChainRepository repository;

    public ChainController(ChainRepository chainRepository) {
        repository = chainRepository;
    }

    @GetMapping("/{id}")
    Chain getName(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    List<Chain> getChains()  {
      return repository.findAll();
    }

    @GetMapping("/dto")
    List<ChainName> getAllDtoNames(){
        try {
            return repository.findAllNamesBy();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto/{id}")
    ChainName getOneDtoName(@PathVariable long id){
        try {
            return repository.findNameById(id);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}/members")
    Chain listMembersOfChain(@PathVariable String id) {
        return repository.findChainById(id);
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
