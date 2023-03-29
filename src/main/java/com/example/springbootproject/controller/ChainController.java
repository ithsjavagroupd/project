package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.projection.ChainName;
import com.example.springbootproject.repository.ChainRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

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
    List<Chain> getChains() {
        return repository.findAll();
    }

    @GetMapping("/dto")
    List<ChainName> getAllDtoNames() {
        try {
            return repository.findAllNamesBy();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dto/{id}")
    ChainName getOneDtoName(@PathVariable long id) {
        try {
            return repository.findNameById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}/members")
    Chain listMembersOfChain(@PathVariable long id) {
        return repository.findChainById(id);
    }

    @PostMapping
    ResponseEntity<Void> addChain(@RequestBody Chain chain) {
        System.out.println("HELLO");
        String name = chain.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(chain);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(chain.getId()).toUri();
        return ResponseEntity.created(location).build();

        //response.setHeader("Location", location.toString());
        //return new ResponseEntity<>(chain, HttpStatus.CREATED);
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
        updateChain.setMembers(chain.getMembers());

        repository.save(updateChain);

        return ResponseEntity.ok(updateChain);
    }


}
