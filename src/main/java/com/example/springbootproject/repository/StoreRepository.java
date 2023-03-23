package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Store;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface StoreRepository extends ListCrudRepository<Store, Long> {

}
