package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Chain;
import org.springframework.data.repository.ListCrudRepository;

public interface FoodChainRepository extends ListCrudRepository<Chain, Long> {
}
