package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Branch;
import com.example.springbootproject.projection.BranchName;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BranchRepository extends ListCrudRepository<Branch, Long> {
    List<BranchName> findAllNamesBy();
    BranchName findNamesById(long id);

}
