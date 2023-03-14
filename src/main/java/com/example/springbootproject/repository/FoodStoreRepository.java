package com.example.springbootproject.repository;

import com.example.springbootproject.entity.FoodStore;
import org.springframework.data.repository.ListCrudRepository;

public interface FoodStoreRepository extends ListCrudRepository<FoodStore, Long> {
}
