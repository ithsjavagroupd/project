package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Chain;
import org.springframework.data.repository.ListCrudRepository;

public interface ChainRepository extends ListCrudRepository<Chain, Long> {
}
