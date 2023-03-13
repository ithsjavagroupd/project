package com.example.springbootproject.repository;

import com.example.springbootproject.entity.FoodChain;
import org.springframework.data.repository.ListCrudRepository;

public interface FoodChainRepository extends ListCrudRepository<FoodChain, Long> {
}
