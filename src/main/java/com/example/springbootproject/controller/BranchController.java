package com.example.springbootproject.controller;


import com.example.springbootproject.entity.Branch;
import com.example.springbootproject.projection.BranchName;
import com.example.springbootproject.repository.BranchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/branches")
public class BranchController {
    private final BranchRepository repository;

    public BranchController(BranchRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    Branch getABranch(@PathVariable long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    List<Branch> getBranches() {
        return repository.findAll();
    }

    @GetMapping("/dto")
    List<BranchName> getAllDtoBranch(){
        return repository.findAllNamesBy();
    }

    @PostMapping
    void addBranch(@RequestBody Branch branch) {
        String name = branch.getName();
        if (name == null || name.isEmpty())
            throw new IllegalStateException();
        repository.save(branch);
    }

    @DeleteMapping("/{id}")
    void deleteBranch(@PathVariable Long id) {
        if (repository.findById(id).isPresent())
            repository.deleteById(id);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch branchDetails) {
        var updateBranch = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        updateBranch.setName(branchDetails.getName());

        repository.save(updateBranch);
        return ResponseEntity.ok(updateBranch);
    }
}


