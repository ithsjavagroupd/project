package com.example.springbootproject.controller;

import com.example.springbootproject.entity.Employee;
import com.example.springbootproject.repository.EmployeeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository repository;


    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    //CRUD

    //Create
    @PostMapping
    Employee addEmployee(@RequestBody Optional<Employee> employee){
        if(employee.isEmpty())
            throw new IllegalStateException();
        return repository.save(employee.get());
    }

    //R
    @GetMapping("/{id}")
    Employee getEmployeeById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @GetMapping
    private List<Employee> getEmployees() {
        return repository.findAll();
    }

    //U
    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee){
        var updatedEmployee = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setAddress(employee.getAddress());
        updatedEmployee.setPhoneNumber(employee.getPhoneNumber());
        repository.save(updatedEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    //D
    @DeleteMapping("/{id}")
    ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
        var toDelete = repository.findById(id);
        if (toDelete.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}