package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.projection.ChainName;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ChainRepository extends ListCrudRepository<Chain, Long> {

    @EntityGraph(value = "Chain.members")
    List<Chain> findAll();


    // Update method with DTO URI
    @EntityGraph(attributePaths = {"chains"})
    List<ChainName> findNamesBy();
}
