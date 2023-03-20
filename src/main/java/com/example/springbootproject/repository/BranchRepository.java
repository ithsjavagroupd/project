package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Branch;
import org.springframework.data.repository.ListCrudRepository;

public interface BranchRepository extends ListCrudRepository<Branch, Long> {

}
