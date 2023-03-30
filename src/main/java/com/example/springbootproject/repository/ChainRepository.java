package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Chain;
import com.example.springbootproject.projection.ChainName;
import com.example.springbootproject.projection.StoreName;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChainRepository extends ListCrudRepository<Chain, Long> {

    @Override
    @EntityGraph(value = "Chain.all")
    Optional<Chain> findById(Long aLong);

    @EntityGraph(value = "Chain.all")
    List<Chain> findAll();

    @EntityGraph(attributePaths = {"members"})
    Chain findChainById(long id);

    @EntityGraph(attributePaths = {"stores"})
    Chain findStoresChainById(long id);

    List<ChainName> findAllNamesBy();

    ChainName findNameById(long id);

}
