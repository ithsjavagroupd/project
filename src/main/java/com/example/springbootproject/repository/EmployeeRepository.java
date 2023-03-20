package com.example.springbootproject.repository;

import com.example.springbootproject.entity.Employee;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends ListCrudRepository<Employee, Long> {

    List<Employee> findAllById(Long longs);

}