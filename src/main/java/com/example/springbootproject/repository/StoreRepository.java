package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Store;
import org.springframework.data.repository.ListCrudRepository;

public interface StoreRepository extends ListCrudRepository<Store, Long> {
}
